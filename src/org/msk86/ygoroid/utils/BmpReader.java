package org.msk86.ygoroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import org.msk86.ygoroid.size.Size;

public class BmpReader {


    public static Bitmap readBitmap(int resId, Size require) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            options.inSampleSize = calculateSampleScale(resId, require);
            Bitmap bitmap = BitmapFactory.decodeResource(Utils2.getContext().getResources(), resId, options);
            return scale(bitmap, require);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap readBitmap(String file, Size require) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            options.inSampleSize = calculateSampleScale(file, require);
            Bitmap bitmap = BitmapFactory.decodeFile(file, options);
            return scale(bitmap, require);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postRotate(degree);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        Bitmap sampledBmp = newBitmap.copy(Bitmap.Config.ARGB_4444, false);
        newBitmap.recycle();
        return sampledBmp;
    }

    public static Bitmap scale(Bitmap bitmap, Size size) {
        Matrix matrix = new Matrix();

        float changeRateW = size.width() * 1.0f / bitmap.getWidth();
        float changeRateH = size.height() * 1.0f / bitmap.getHeight();
        matrix.postScale(changeRateW, changeRateH);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        Bitmap sampledBmp = newBitmap.copy(Bitmap.Config.ARGB_4444, false);
        newBitmap.recycle();
        return sampledBmp;
    }

    private static int calculateSampleScale(BitmapFactory.Options options, Size require) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSizeH = 1, inSampleSizeW = 1;
        if (height > require.height()) {
            inSampleSizeH = Math.round((float) height / (float) require.height());
        }
        if (width > require.width()) {
            inSampleSizeW = Math.round((float) width / (float) require.width());
        }
        return Math.min(inSampleSizeW, inSampleSizeH);
    }


    private static int calculateSampleScale(int resId, Size require) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(Utils2.getContext().getResources(), resId, options);
        return calculateSampleScale(options, require);
    }

    private static int calculateSampleScale(String file, Size require) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        return calculateSampleScale(options, require);
    }
}
