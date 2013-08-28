package org.msk86.ygoroid.core.tool;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.msk86.ygoroid.core.Drawable;
import org.msk86.ygoroid.core.SelectableItem;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

import java.util.Random;

public class Coin implements SelectableItem, Drawable {
    private int coinNumber;
    private Random random;
    private Mask mask;

    public Coin() {
        random = new Random();
        mask = new Mask();
        coinNumber = 1;
    }

    public void throwCoin() {
        coinNumber = random.nextInt(2);
        mask.restart();
    }


    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawCoinFrame(canvas, x, y);
        drawCoin(canvas, x, y);
        mask.draw(canvas, x, y);
    }

    @Override
    public int width() {
        return Utils.unitLength() / 2;
    }

    @Override
    public int height() {
        return Utils.unitLength() / 2;
    }

    private int coinRadius() {
        return Utils.unitLength() / 4;
    }

    private int coinInnerRadius() {
        return Utils.unitLength() / 5;
    }

    private void drawCoin(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(coinRadius() / 3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        Utils.DrawHelper drawHelper = new Utils.DrawHelper(x, y);

        drawHelper.drawLine(canvas, coinRadius() / 3, coinRadius(), coinRadius() * 5 / 3, coinRadius(), paint);

        if (coinNumber == 1) {
            drawHelper.drawLine(canvas, coinRadius(), coinRadius() / 3, coinRadius(), coinRadius() * 5 / 3, paint);
        }
    }

    private void drawCoinFrame(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int padding = 2;

        Utils.DrawHelper drawHelper = new Utils.DrawHelper(x, y);

        drawHelper.drawCircle(canvas, coinRadius(), coinRadius(), coinRadius() - padding, paint);
        paint.setStrokeWidth(2);
        drawHelper.drawCircle(canvas, coinRadius(), coinRadius(), coinInnerRadius() - padding, paint);
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
