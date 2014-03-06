package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Dice;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;

public class DiceRenderer implements Renderer {
    Dice dice;

    public DiceRenderer(Dice dice) {
        this.dice = dice;
    }

    @Override
    public Size size() {
        return OtherSize.DICE;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawDiceFrame(canvas, x, y);
        drawDice(canvas, x, y);
    }

    private void drawDice(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Style.lineColor());
        paint.setAntiAlias(true);

        canvas.save();
        canvas.translate(x, y);

        int radius = pointRadius();

        switch (dice.getDiceNumber()) {
            case 1:
                canvas.drawCircle(size().width() / 2, size().height() / 2, radius, paint);
                break;
            case 2:
                canvas.drawCircle(size().width() / 3, size().height() / 3, radius, paint);
                canvas.drawCircle(size().width() * 2 / 3, size().height() * 2 / 3, radius, paint);
                break;
            case 3:
                canvas.drawCircle(size().width() * 7 / 24, size().height() * 7 / 24, radius, paint);
                canvas.drawCircle(size().width() / 2, size().height() / 2, radius, paint);
                canvas.drawCircle(size().width() * 17 / 24, size().height() * 17 / 24, radius, paint);
                break;
            case 4:
                canvas.drawCircle(size().width() / 4, size().height() / 4, radius, paint);
                canvas.drawCircle(size().width() / 4, size().height() * 3 / 4, radius, paint);
                canvas.drawCircle(size().width() * 3 / 4, size().height() / 4, radius, paint);
                canvas.drawCircle(size().width() * 3 / 4, size().height() * 3 / 4, radius, paint);
                break;
            case 5:
                canvas.drawCircle(size().width() / 4, size().height() / 4, radius, paint);
                canvas.drawCircle(size().width() / 4, size().height() * 3 / 4, radius, paint);
                canvas.drawCircle(size().width() / 2, size().height() / 2, radius, paint);
                canvas.drawCircle(size().width() * 3 / 4, size().height() / 4, radius, paint);
                canvas.drawCircle(size().width() * 3 / 4, size().height() * 3 / 4, radius, paint);
                break;
            default:
                canvas.drawCircle(size().width() / 3, size().height() / 4, radius, paint);
                canvas.drawCircle(size().width() / 3, size().height() / 2, radius, paint);
                canvas.drawCircle(size().width() / 3, size().height() * 3 / 4, radius, paint);
                canvas.drawCircle(size().width() * 2 / 3, size().height() / 4, radius, paint);
                canvas.drawCircle(size().width() * 2 / 3, size().height() / 2, radius, paint);
                canvas.drawCircle(size().width() * 2 / 3, size().height() * 3 / 4, radius, paint);
        }
        canvas.restore();
    }

    private void drawDiceFrame(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Style.lineColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        canvas.save();
        canvas.translate(x, y);

        canvas.drawRoundRect(new RectF(Style.padding(), Style.padding(),
                size().width() - Style.padding(), size().height() - Style.padding()),
                7, 7, paint);
        canvas.restore();
    }

    private int pointRadius() {
        switch (dice.getDiceNumber()) {
            case 1:
                return size().width() / 4;
            case 2:
                return size().width() / 6;
            case 3:
            case 4:
                return size().width() / 8;
            default:
                return size().width() / 9;
        }
    }
}
