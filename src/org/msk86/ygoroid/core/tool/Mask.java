package org.msk86.ygoroid.core.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.core.Drawable;
import org.msk86.ygoroid.utils.Utils;

public class Mask implements Drawable {
    private static int MAX_THROWING = 30;
    private static Bitmap[] MASKS;
    private int throwing;

    public Mask() {
        throwing = MAX_THROWING + 1;
        prepareMask();
    }

    public void restart() {
        throwing = 1;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper drawHelper = new Utils.DrawHelper(x, y);
        if (throwing <= MAX_THROWING) {
            drawHelper.drawBitmap(canvas, MASKS[throwing - 1], 0, 0, new Paint());
            throwing++;
        }
    }

    @Override
    public int width() {
        return Utils.unitLength() / 2;
    }

    @Override
    public int height() {
        return Utils.unitLength() / 2;
    }

    private synchronized void prepareMask() {
        if(MASKS != null) {
            return;
        }
        Bitmap mask = Utils.readBitmapScaleByHeight(R.raw.mask, height());
        MASKS = new Bitmap[MAX_THROWING];
        Paint paint = new Paint();
        for(int i=1;i<=MAX_THROWING;i++) {
            int stepWidth = width() * 2 * i / MAX_THROWING;
            Bitmap maskStep = Bitmap.createBitmap(width(), height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(maskStep);
            canvas.drawBitmap(mask, stepWidth - width() , 0, paint);
            MASKS[i - 1] = maskStep;
        }
    }

}
