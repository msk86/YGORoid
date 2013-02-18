package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class LifePoint implements SelectableItem {
    int lp;

    public LifePoint() {
        this(8000);
    }

    public LifePoint(int lp) {
        this.lp = lp;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    @Override
    public String toString() {
        return "LP: " + lp;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap lpBmp = Bitmap.createBitmap((int)(Utils.unitLength() * 1.2), Utils.unitLength() / 4, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(lpBmp);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(Utils.unitLength() / 4);
        textPaint.setColor(Configuration.fontColor());
        canvas.drawText(toString(), 8, Utils.unitLength() / 4, textPaint);
        return lpBmp;
    }

    @Override
    public void select() {
    }

    @Override
    public void unSelect() {
    }

    @Override
    public boolean isSelect() {
        return false;
    }
}
