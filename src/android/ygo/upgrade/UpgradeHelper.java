package android.ygo.upgrade;

import android.ygo.YGOActivity;

public class UpgradeHelper {

    private UpgradeChecker upgradeChecker;
    private DatabaseChecker databaseChecker;
    private PicChecker picChecker;

    public UpgradeHelper(YGOActivity context) {
        upgradeChecker = new UpgradeChecker(context);
        databaseChecker = new DatabaseChecker(context);
        picChecker = new PicChecker(context);
    }

    public void startCheck() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(upgradeChecker.checkUpgrade()) {
                    return;
                }
                databaseChecker.checkUpgrade();
                picChecker.checkUpgrade();
            }
        }).start();
    }

    public UpgradeChecker getUpgradeChecker() {
        return upgradeChecker;
    }

    public DatabaseChecker getDatabaseChecker() {
        return databaseChecker;
    }

    public PicChecker getPicChecker() {
        return picChecker;
    }
}
