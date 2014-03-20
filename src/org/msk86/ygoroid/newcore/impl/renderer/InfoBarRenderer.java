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
        int fontSize = (int) (size().height() / 1.3);
        paint.setTextSize(fontSize);

        String versionText = "V" + Utils.getVersion();
        canvas.save();
        canvas.translate(x, y + 1);
        StaticLayout layout = new StaticLayout(versionText, paint, size().width() - 4, Layout.Alignment.ALIGN_OPPOSITE, 1, 0, false);
        layout.draw(canvas);

        int versionWidth = TextUtils.getTextWidth(versionText, paint);
        String infoText = infoBar.info();
        canvas.translate(4, 0);
        layout = new StaticLayout(infoText, paint, size().width() - versionWidth - 8, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        layout = TextUtils.scaleToOneLine(layout);
        layout.draw(canvas);

        canvas.restore();
    }

    private void drawFrame(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);

        Paint paint = new Paint();
        paint.setColor(Style.infoBarBackgroundColor());
        paint.setAlpha(180);
        canvas.drawRect(new Rect(0, 0, size().width(), size().height()), paint);

        paint.setColor(Style.infoBarBorderColor());
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(255);
        canvas.drawRect(new Rect(0, 0, size().width(), size().height() + 3), paint);

        canvas.restore();
    }
}
