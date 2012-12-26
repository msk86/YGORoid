package android.ygo.core;

import android.graphics.*;

public class Field implements Item {

    @Override
    public Bitmap toBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        canvas.drawLine(5, 5, 95, 5, paint);
        canvas.drawLine(95, 5, 95, 95, paint);
        canvas.drawLine(95, 95, 5, 95, paint);
        canvas.drawLine(5,95,5,5,paint);
        return bitmap;
    }
}
