package org.msk86.ygoroid.newutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import org.msk86.ygoroid.size.Size;

public class BmpReader {


    public static Bitmap readBitmap(String file, int defaultResId, Size size) {
        Bitmap bmp = readBitmap(file, size);
        if(bmp == null) {
            bmp = readBitmap(defaultResId, size);
        }
        return bmp;
    }
    public static Bitmap readScaleBitmap(String file, int defaultResId, Size size) {
        Bitmap bmp = readScaleBitmap(file, size);
        if(bmp == null) {
            bmp = readScaleBitmap(defaultResId, size);
        }
        return bmp;
    }

    public static Bitmap readBitmap(int resId, Size require) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateSampleScale(resId, require);
            Bitmap bitmap = BitmapFactory.decodeResource(Utils.getContext().getResources(), resId, options);
            return scale(bitmap, require);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap readScaleBitmap(int resId, Size require) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateSampleScale(resId, require);
            Bitmap bitmap = BitmapFactory.decodeResource(Utils.getContext().getResources(), resId, options);
            return focusScale(bitmap, require);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap readBitmap(String file, Size require) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateSampleScale(file, require);
            Bitmap bitmap = BitmapFactory.decodeFile(file, options);
            return scale(bitmap, require);
        } catch (Exception e) {
            return null;
        }
    }
    public static Bitmap readScaleBitmap(String file, Size require) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateSampleScale(file, require);
            Bitmap bitmap = BitmapFactory.decodeFile(file, options);
            return focusScale(bitmap, require);
        } catch (Exception e) {
            return null;
        }
    }

    private static Bitmap scale(Bitmap bitmap, Size size) {
        Matrix matrix = new Matrix();

        float changeRateW = size.width() * 1.0f / bitmap.getWidth();
        float changeRateH = size.height() * 1.0f / bitmap.getHeight();
        matrix.postScale(changeRateW, changeRateH);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newBitmap;
    }

    private static Bitmap focusScale(Bitmap bitmap, Size size) {
        Matrix matrix = new Matrix();

        float bmpWHRate = bitmap.getWidth() / bitmap.getHeight();
        float sizeWHRate = size.width() / size.height();

        float rate;
        if(bmpWHRate > sizeWHRate) {
            rate = size.height() * 1.0f / bitmap.getHeight();
        } else {
            rate = size.width() * 1.0f / bitmap.getWidth();
        }

        matrix.postScale(rate, rate);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newBitmap;
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
        BitmapFactory.decodeResource(Utils.getContext().getResources(), resId, options);
        return calculateSampleScale(options, require);
    }

    private static int calculateSampleScale(String file, Size require) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        return calculateSampleScale(options, require);
    }
}
