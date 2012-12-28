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
        int unitLengthH = (int)(dm.heightPixels / 4f);
        return unitLengthW < unitLengthH ? unitLengthW : unitLengthH;
    }

    public static int cardHeight() {
        int padding = 2;
        return unitLength() - padding * 2;
    }

    public static Bitmap readBitmapScaleByHeight(String file, int targetHeight) {
        file = baseDir + file;
        Bitmap bitmap = BitmapFactory.decodeFile(file);
        return scaleByHeight(bitmap, targetHeight);
    }

    public static Bitmap scaleByHeight(Bitmap bitmap, int targetHeight) {
        Matrix matrix = new Matrix();
        float changeRate = targetHeight * 1.0f / bitmap.getHeight();
        matrix.postScale(changeRate, changeRate);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newBitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postRotate(degree);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newBitmap;
    }
}
