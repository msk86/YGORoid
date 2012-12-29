package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class DeckField extends Field {
    private String fieldName;
    public DeckField(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    @Override
    public Bitmap toBitmap(){
        Bitmap bitmap = super.toBitmap();

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawText(fieldName, 5, 15, paint);

        return bitmap;
    }
}
