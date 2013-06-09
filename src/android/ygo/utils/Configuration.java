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

    public static int textShadowColor() {
        return Color.BLACK;
    }

    public static int lineColor() {
        return Color.WHITE;
    }

    public static int cardSelectorBackgroundColor() {
        return Color.GRAY;
    }

    public static String device() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        }
        return "/Device/";
    }

    public static String baseDir() {
        return device() + "YGORoid/";
    }

    public static String deckPath() {
        return baseDir() + "deck/";
    }

    public static String cardImgPath() {
        return baseDir() + "pics/";
    }

    public static String userDefinedCardImgPath() {
        return baseDir() + "userDefined/";
    }

    public static String cardImageSuffix() {
        return ".jpg";
    }

    public static boolean showFPS() {
        return false;
    }
}
