package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Coin;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;

public class CoinRenderer implements Renderer {
    Coin coin;

    public CoinRenderer(Coin coin) {
        this.coin = coin;
    }

    @Override
    public Size size() {
        return OtherSize.COIN;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawCoinFrame(canvas, x, y);
        drawCoin(canvas, x, y);
        coin.getMask().getRenderer().draw(canvas, x, y);
    }

    private void drawCoin(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Style.lineColor());
        paint.setStrokeWidth(extorialRadius() / 3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        canvas.save();
        canvas.translate(x, y);

        Point center = centerPosition();
        canvas.drawLine(center.x - coinLineRadius(), center.y, center.x + coinLineRadius(), center.y, paint);

        if (coin.getCoinNumber() == 1) {
            canvas.drawLine(center.x, center.y - coinLineRadius(), center.x, center.y + coinLineRadius(), paint);
        }

        canvas.restore();
    }

    private void drawCoinFrame(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Style.lineColor());
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        canvas.save();
        canvas.translate(x, y);

        Point center = centerPosition();
        canvas.drawCircle(center.x, center.y, extorialRadius(), paint);

        paint.setStrokeWidth(2);
        canvas.drawCircle(center.x, center.y, interialRadius(), paint);
        canvas.restore();
    }

    private Point centerPosition() {
        return new Point(size().width() / 2, size().height() / 2);
    }

    private int extorialRadius() {
        return size().height() / 2 - Style.padding();
    }

    private int interialRadius() {
        return (int) (size().height() / 2.5) - Style.padding();
    }

    private int coinLineRadius() {
        return size().height() / 3;
    }
}
