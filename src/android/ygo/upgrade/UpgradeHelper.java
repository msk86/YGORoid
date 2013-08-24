package android.ygo.upgrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.ygo.R;
import android.ygo.YGOActivity;
import android.ygo.utils.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
