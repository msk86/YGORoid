package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.InfoBar;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.TextUtils;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.size.FieldSize;
import org.msk86.ygoroid.size.InfoBarSize;
import org.msk86.ygoroid.size.Size;

public class InfoBarRenderer implements Renderer {
    InfoBar infoBar;

    public InfoBarRenderer(InfoBar infoBar) {
        this.infoBar = infoBar;
    }

    @Override
    public Size size() {
        int w = infoBar.getBarHolder().getRenderer().size().width();
        return new Size(w, InfoBarSize.INFO_BAR.height());
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawFrame(canvas, x, y);
        drawText(canvas, x, y);
    }

    private void drawText(Canvas canvas, int x, int y) {
        TextPaint paint = new TextPaint();
        paint.setColor(Style.fontColor());
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        int fontSize = (int) (size().height() / 1.2);
        paint.setTextSize(fontSize);

        String versionText = "V" + Utils.getVersion();
        String infoText = TextUtils.cutOneLine(infoBar.info(), paint, size().width());
        StaticLayout layout = new StaticLayout(infoText, paint, size().width() - fontSize * versionText.length() - 8, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        canvas.save();
        canvas.translate(x + 4, y + 1);
        layout.draw(canvas);
        canvas.translate(-4, 0);

        layout = new StaticLayout(versionText, paint, size().width() - 4, Layout.Alignment.ALIGN_OPPOSITE, 1, 0, false);
        layout.draw(canvas);
        canvas.restore();
    }

    protected void drawVersion(Canvas canvas) {
        TextPaint paint = new TextPaint();
        paint.setColor(Style.fontColor());
        int fontSize = FieldSize.SQUARE.width() / 7;
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        String versionText = "V" + Utils.getVersion();
        StaticLayout layout = new StaticLayout(versionText, paint, fontSize * 5, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        canvas.save();
        canvas.translate(4, 0);
        layout.draw(canvas);
        canvas.restore();
    }

    private void drawFrame(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);

        Paint paint = new Paint();
        paint.setColor(Style.infoBarBackgroundColor());
        canvas.drawRect(new Rect(0, 0, size().width(), size().height()), paint);

        paint.setColor(Style.infoBarBorderColor());
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(new Rect(0, 0, size().width(), size().height() + 3), paint);

        canvas.restore();
    }
}
