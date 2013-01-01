package android.ygo.core;

import android.graphics.Color;

import java.security.PublicKey;

public class Configuration {
    public static boolean isTotalCardPic() {
        return true;
    }

    public static int highlightColor() {
        return Color.BLUE;
    }

    public static int fontColor() {
        return Color.WHITE;
    }

    public static String baseDir() {
        return "/Device/bluetooth/images/ygo2/";
    }

    public static String cardProtector() {
        return "defaultProtector";
    }
}
