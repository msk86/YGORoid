package android.ygo.core;

import android.graphics.*;
import android.ygo.utils.Utils;

public class Field implements Item {
    int index;

    public Field(int index) {
        this.index = index;
    }

    @Override
    public Bitmap toBitmap() {
        Utils u = Utils.getInstance();
        int width = u.scaleX(0.125f);
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
