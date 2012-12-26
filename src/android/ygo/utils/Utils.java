package android.ygo.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utils {
    private static DisplayMetrics dm;

    public static void initInstance(Activity activity) {
        dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public static int unitLength() {
        int unitLengthW = (int)(dm.widthPixels / 6f);
        int unitLengthH = (int)(dm.heightPixels * 0.92f / 4);
        return unitLengthW < unitLengthH ? unitLengthW : unitLengthH;
    }
}
