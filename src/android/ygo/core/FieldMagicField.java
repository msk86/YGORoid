package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class FieldMagicField extends Field {
    public FieldMagicField() {
        super();
    }

    @Override
    public Bitmap toBitmap(){
        Bitmap bitmap = super.toBitmap();

        int width = Utils.unitLength();
        int padding = 2;

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawLine(width / 2, padding, width / 2, width - padding, paint);
        canvas.drawLine(padding, width / 2, width - padding, width / 2,  paint);

        return bitmap;
    }
}
