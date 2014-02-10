package org.msk86.ygoroid.upgrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import org.json.JSONObject;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PicChecker implements Checker {
    private static final String PIC_API = Utils.s(R.string.pics_api);

    private YGOActivity context;

    private Set<String> missingIds;

    private String picUrlTemplate;

    public PicChecker(YGOActivity context) {
        this.context = context;
    }

    @Override
    public boolean checkUpgrade() {
        context.getUpgradeMsgHandler().sendEmptyMessage(PICS);
        return true;
    }

    private void getPicUrlTemplate() {
        try {
            JSONObject picApiInfo = new JSONObject(NetClient.request(PIC_API));
            picUrlTemplate = picApiInfo.getString("url").replace(":id", "%s");
        } catch (Exception e) {
            picUrlTemplate = Utils.s(R.string.pics_url_backup);
        }
    }

    private void getMissingIds() {
        Set<String> cardIdInFile = new HashSet<String>(10000);

        Set<String> cardPics = new HashSet<String>(10000);

        String[] pics = Utils.cardPics();
        Collections.addAll(cardPics, pics);

        String[] zips = Utils.cardPicZips();
        for (String zip : zips) {
            String[] picsInZip = Utils.cardPicsInZip(Configuration.cardImgPath() + zip);
            Collections.addAll(cardPics, picsInZip);
        }

        for (String pic : cardPics) {
            String id = pic.substring(pic.lastIndexOf('/') + 1, pic.lastIndexOf('.'));
            cardIdInFile.add(id);
        }

        Set<String> cardIdInDB = Utils.getDbHelper().getAllCardId();

        cardIdInDB.removeAll(cardIdInFile);
        missingIds = cardIdInDB;
    }

    private boolean shouldUpgrade() {
        return missingIds.size() > 0;
    }

    @Override
    public void upgrade() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                waitForDatabase();
                getMissingIds();
                if (!shouldUpgrade()) {
                    return;
                }
                if (!context.isWifiChecked() && !Utils.isWifiConnected()) {
                    handler.sendEmptyMessage(0);
                } else {
                    download();
                }
            }
        }).start();
    }

    private void waitForDatabase() {
        while (!context.isNewestDatabase()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }


    private void download() {
        getPicUrlTemplate();
        Downloader downloader = context.getDownloader();
        downloader.clear();
        downloader.setThreads(5);
        for (String id : missingIds) {
            String url = String.format(picUrlTemplate, id);
            downloader.addTask(url, Configuration.cardImgPath(), null, null);
        }

        downloader.successCallback(new DownloaderCallback() {
            @Override
            public void callback(int success, int fail, int all) {
                String info = Utils.s(R.string.pics_updated);
                if (fail > 0) {
                    info += String.format(Utils.s(R.string.pics_update_failed), fail);
                }
                context.showInfo(info);
            }
        });
        downloader.progressCallback(new DownloaderCallback() {
            @Override
            public void callback(int success, int fail, int all) {
                context.showInfo(String.format(Utils.s(R.string.pics_updating), success, all));
            }
        });
        downloader.startDownload();
    }

    private void checkWifiDialog() {
        new AlertDialog.Builder(Utils.getContext())
                .setTitle(Utils.s(R.string.wifi_check))
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        download();
                    }
                })
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), null)
                .create().show();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            checkWifiDialog();
        }
    };
}
