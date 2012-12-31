package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Field implements Item {

    private SelectableItem setItem;

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

        if(setItem != null) {
            Bitmap itemBmp = setItem.toBitmap();
            Utils.drawBitmapOnCanvas(canvas, itemBmp, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);
        }

        return bitmap;
    }
}
