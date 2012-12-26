package android.ygo.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

public class Utils {
    private static DisplayMetrics dm;
    private static String baseDir = "/Device/bluetooth/images/";

    public static void initInstance(Activity activity) {
        dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public static int unitLength() {
        int unitLengthW = (int)(dm.widthPixels / 6f);
        int unitLengthH = (int)(dm.heightPixels * 0.92f / 4);
        return unitLengthW < unitLengthH ? unitLengthW : unitLengthH;
    }

    public static int cardHeight() {
        int padding = 2;
        return unitLength() - padding * 2;
    }

    public static Bitmap readBitmapScaleByHeight(String file, int targetHeight) {
        file = baseDir + file;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        options.inSampleSize = (int)(options.outHeight * 1.0f / targetHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }
}
