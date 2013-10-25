package org.msk86.ygoroid.upgrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PicChecker implements Checker {

    private YGOActivity context;

    private Set<String> missingIds;

    public PicChecker(YGOActivity context) {
        this.context = context;
    }

    @Override
    public boolean checkUpgrade() {
        context.getUpgradeMsgHandler().sendEmptyMessage(PICS);
        return true;
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
                if (!context.isWifiChecked() && !Utils.isWifiEnable()) {
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
        Downloader downloader = context.getDownloader();
        downloader.clear();
        for (String id : missingIds) {
            String url = String.format(Utils.s(R.string.pics_api), id);
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
        downloader.startDownload(Utils.s(R.string.pics_updating));
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
