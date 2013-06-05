package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class Field implements Item {
    private static Bitmap FRAME;

    static {
        FRAME = fieldFrameBmp();
    }

    private FieldType type;
    private SelectableItem setItem;

    public Field(FieldType type) {
        this.type = type;
    }

    public FieldType getType() {
        return type;
    }

    public void setItem(SelectableItem item) {
        this.setItem = item;
    }

    public SelectableItem getItem() {
        return setItem;
    }

    public SelectableItem removeItem() {
        SelectableItem item = this.setItem;
        this.setItem = null;
        return item;
    }

    public Bitmap toBitmap() {
        int width = Utils.unitLength();
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        Utils.drawBitmapOnCanvas(canvas, FRAME, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);
        if (setItem != null) {
            Bitmap itemBmp = setItem.toBitmap();
            Utils.drawBitmapOnCanvas(canvas, itemBmp, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);
        }

        return bitmap;
    }

    private static Bitmap fieldFrameBmp() {
        int width = Utils.unitLength();
        int padding = 2;
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(padding, padding, width - padding, width - padding, paint);

        return bitmap;
    }
}
