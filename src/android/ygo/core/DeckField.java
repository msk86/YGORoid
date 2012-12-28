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

        int width = Utils.unitLength();
        int padding = 2;

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawLine(padding, padding, width - padding, width - padding, paint);
        canvas.drawLine(padding, width - padding, width - padding, padding, paint);
        canvas.drawText(fieldName, 10, 15, paint);

        return bitmap;
    }
}
