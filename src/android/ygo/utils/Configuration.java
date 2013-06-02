package android.ygo.utils;

import android.graphics.Color;
import android.os.Environment;

public class Configuration {
    public static int highlightColor() {
        return Color.BLUE;
    }

    public static int fontColor() {
        return Color.WHITE;
    }

    public static String device() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        }
        return "/Device/";
    }

    public static String baseDir() {
        return device() + ".YGORoid/";
    }

    public static String deckPath() {
        return baseDir() + "deck/";
    }

    public static String cardImgPath() {
        return baseDir() + "img/";
    }

    public static boolean isMirror() {
        return false;
    }
}
