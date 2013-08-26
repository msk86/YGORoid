package android.ygo.upgrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ygo.R;
import android.ygo.YGOActivity;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class DatabaseChecker implements Checker {
    private static final String DATABASE_CHECK_URL = Utils.s(R.string.database_check);

    private YGOActivity context;

    private String databaseUpgradeUrl;
    private long latestDatabaseSize;
    private long latestDatabaseCount;

    public DatabaseChecker(YGOActivity context) {
        this.context = context;
    }

    public boolean checkUpgrade() {
        try {
            JSONObject databaseInfo = new JSONObject(NetClient.request(DATABASE_CHECK_URL));
            databaseUpgradeUrl = databaseInfo.getString("upgrade_url");
            latestDatabaseSize = databaseInfo.getLong("size");
            latestDatabaseCount = databaseInfo.getLong("count");

            if (shouldUpgrade()) {
                context.getUpgradeMsgHandler().sendEmptyMessage(Checker.DATA_BASE);
                return true;
            } else {
                context.setNewestDatabase();
            }
        } catch (Exception e) {
        }

        return false;
    }

    private boolean shouldUpgrade() {
        File db = new File(Configuration.baseDir() + Configuration.databaseName());
        if (!db.exists()) {
            return true;
        } else {
            int count = Utils.getDbHelper().countAll();
            return count < latestDatabaseCount;
        }
    }

    public void upgrade() {
        if(!Utils.isWifiEnable()) {
            checkWifiDialog();
        } else {
            download();
        }
    }

    private void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context.getDownloader().downFile(databaseUpgradeUrl, Configuration.baseDir(), Configuration.databaseName(), new DownloadProgress() {
                        @Override
                        public void display(YGOActivity context, String file, long fileSize, long progress) {
                            context.showInfo(String.format(Utils.s(R.string.database_updating), file, (100.0 * progress / latestDatabaseSize)));
                        }

                        @Override
                        public void error(YGOActivity context, String info) {
                            context.showInfo(info);
                        }
                    });
                    context.showInfo(String.format(Utils.s(R.string.database_updated), Configuration.databaseName()));
                    context.setNewestDatabase();
                } catch (IOException e) {
                }
            }
        }).start();
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

}
