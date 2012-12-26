package android.ygo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    public static Utils utils;

    private DisplayMetrics dm;

    private Utils(Activity activity) {
        dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public static void initInstance(Activity activity) {
        utils = new Utils(activity);
    }

    public static Utils getInstance(){
        return utils;
    }

    public int scaleX(float xPercentage) {
        return (int)(dm.widthPixels * xPercentage);
    }

    public int scaleY(float yPercentage) {
        return (int)(dm.heightPixels * yPercentage);
    }
}
