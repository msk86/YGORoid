package org.msk86.ygoroid.core.tool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import org.msk86.ygoroid.core.Drawable;
import org.msk86.ygoroid.core.SelectableItem;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

import java.util.Random;

public class Dice implements SelectableItem, Drawable {
    private int diceNumber;
    private Random random;
    private Mask mask;

    public Dice() {
        mask = new Mask();
        random = new Random();
        diceNumber = 5;
    }

    public void throwDice() {
        diceNumber = random.nextInt(6);
        mask.restart();
    }


    @Override
    public int width() {
        return Utils.unitLength() / 2;
    }

    @Override
    public int height() {
        return Utils.unitLength() / 2;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawDiceFrame(canvas, x, y);
        drawDice(canvas, x, y);
        mask.draw(canvas, x, y);
    }

    private void drawDiceFrame(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int padding = 2;

        Utils.DrawHelper drawHelper = new Utils.DrawHelper(x, y);
        drawHelper.drawRoundRect(canvas,
                new RectF(padding, padding, width() - padding, height() - padding),
                7, 7, paint);
    }

    private void drawDice(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setAntiAlias(true);
        Utils.DrawHelper drawHelper = new Utils.DrawHelper(x, y);

        int radius = pointRadius();

        switch (diceNumber) {
            case 0:
                drawHelper.drawCircle(canvas, width() / 2, height() / 2, radius, paint);
                break;
            case 1:
                drawHelper.drawCircle(canvas, width() / 3, height() / 3, radius, paint);
                drawHelper.drawCircle(canvas, width() * 2 / 3, height() * 2 / 3, radius, paint);
                break;
            case 2:
                drawHelper.drawCircle(canvas, width() * 7 / 24, height() * 7 / 24, radius, paint);
                drawHelper.drawCircle(canvas, width() / 2, height() / 2, radius, paint);
                drawHelper.drawCircle(canvas, width() * 17 / 24, height() * 17 / 24, radius, paint);
                break;
            case 3:
                drawHelper.drawCircle(canvas, width() / 4, height() / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() / 4, height() * 3 / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() * 3 / 4, height() / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() * 3 / 4, height() * 3 / 4, radius, paint);
                break;
            case 4:
                drawHelper.drawCircle(canvas, width() / 4, height() / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() / 4, height() * 3 / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() / 2, height() / 2, radius, paint);
                drawHelper.drawCircle(canvas, width() * 3 / 4, height() / 5, radius, paint);
                drawHelper.drawCircle(canvas, width() * 3 / 4, height() * 3 / 4, radius, paint);
                break;
            default:
                drawHelper.drawCircle(canvas, width() / 3, height() / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() / 3, height() / 2, radius, paint);
                drawHelper.drawCircle(canvas, width() / 3, height() * 3 / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() * 2 / 3, height() / 4, radius, paint);
                drawHelper.drawCircle(canvas, width() * 2 / 3, height() / 2, radius, paint);
                drawHelper.drawCircle(canvas, width() * 2 / 3, height() * 3 / 4, radius, paint);
        }
    }

    private int pointRadius() {
        switch (diceNumber) {
            case 0:
                return width() / 4;
            case 1:
                return width() / 6;
            case 2:
            case 3:
                return width() / 8;
            default:
                return width() / 9;
        }
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
