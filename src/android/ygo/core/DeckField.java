package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
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

        CharSequence cs = fieldName;
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);

        canvas.translate(0, 5);
        StaticLayout layout = new StaticLayout(cs, textPaint, Utils.unitLength(), Layout.Alignment.ALIGN_CENTER, 0,0,false);
        layout.draw(canvas);

        return bitmap;
    }
}
