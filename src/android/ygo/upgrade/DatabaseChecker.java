package android.ygo.upgrade;

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

    private JSONObject databaseInfo = null;
    private String databaseUpgradeUrl;
    private long latestDatabaseSize;

    public DatabaseChecker(YGOActivity context) {
        this.context = context;
    }

    public boolean checkUpgrade() {
        try {
            databaseInfo = new JSONObject(NetClient.request(DATABASE_CHECK_URL));
            databaseUpgradeUrl = databaseInfo.getString("upgrade_url");
            latestDatabaseSize = databaseInfo.getLong("size");

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
            long dbSize = db.length();
            return dbSize < latestDatabaseSize;
        }
    }

    public void upgrade() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context.getDownloader().downFile(databaseUpgradeUrl, Configuration.baseDir(), Configuration.databaseName(), new DownloadProgress() {
                        @Override
                        public void display(YGOActivity context, String file, long fileSize, long progress) {
                            String info = String.format(Utils.s(R.string.database_updating), file, (100.0 * progress / latestDatabaseSize));
                            context.showInfo(info);
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

}
