package org.msk86.ygoroid.newutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.newsqlite.CardsDBHelper;

import java.io.File;
import java.io.FilenameFilter;

public class Utils {
    private static DisplayMetrics dm;
    private static YGOActivity context;
    private static CardsDBHelper dbHelper;

    public static void initInstance(YGOActivity activity) {
        context = activity;
        dm = new DisplayMetrics();
        dbHelper = new CardsDBHelper(activity, 1);
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        checkFolders();
    }

    private static void checkFolders() {
        checkFolder(Configuration.baseDir(), false);
        checkFolder(Configuration.deckPath(), false);
        checkFolder(Configuration.cardImgPath(), true);
        checkFolder(Configuration.userDefinedCardImgPath(), true);
        checkFolder(Configuration.texturePath(), true);
    }

    private static void checkFolder(String path, boolean noMedia) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (noMedia) {
            File noMediaDir = new File(path + ".nomedia");
            if (!noMediaDir.exists()) {
                noMediaDir.mkdirs();
            }
        }
    }

    public static YGOActivity getContext() {
        return context;
    }

    public static CardsDBHelper getDbHelper() {
        return dbHelper;
    }

    public static int screenHeight() {
        return dm.heightPixels;
    }

    public static int screenWidth() {
        return dm.widthPixels;
    }

    public static String[] decks() {
        File deckPath = new File(Configuration.deckPath());
        return deckPath.list();
    }

    public static String[] cardPics() {
        File picsDir = new File(Configuration.cardImgPath());
        return picsDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(".jpg");
            }
        });
    }

    public static void deleteDeck(String deckName) {
        File deckFile = new File(Configuration.deckPath() + deckName);
        if (deckFile.isFile() && deckFile.exists()) {
            deckFile.delete();
        }
    }

    public static int getSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getVersion() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            return "0";
        }
    }

    public static String s(int id) {
        return context.getResources().getString(id);
    }

    public static boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
}
