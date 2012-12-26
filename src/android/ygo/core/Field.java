package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Field implements Item {
    int index;


    public Field(int index) {
        this.index = index;
    }

    public Field() {
        this(0);
    }

    public Bitmap toBitmap() {
        int width = Utils.unitLength();
        int padding = 2;
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        canvas.drawLine(padding, padding, width - padding, padding, paint);
        canvas.drawLine(width - padding, padding, width - padding, width - padding, paint);
        canvas.drawLine(width - padding, width - padding, padding, width - padding, paint);
        canvas.drawLine(padding, width - padding, padding, padding, paint);
        return bitmap;
    }
}
