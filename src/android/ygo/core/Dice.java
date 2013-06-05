package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.Random;

public class Dice implements SelectableItem {
    private static int DICE_WIDTH = Utils.unitLength() / 2;
    private static Bitmap[] DICE_BMPS = new Bitmap[6];
    private static Bitmap DICE_FRAME;
    private static long MAX_DICE_TIME = 1000;

    static {
        for (int i = 0; i < DICE_BMPS.length; i++) {
            DICE_BMPS[i] = dicePoint(i);
        }
        DICE_FRAME = diceFrame();
    }

    private int diceNumber;

    private int throwing;
    private long startDiceTime;



    public Dice() {
        diceNumber = 5;
    }

    public void throwDice() {
        throwing = diceNumber;
        diceNumber = new Random().nextInt(6);
        startDiceTime = System.currentTimeMillis();

    }

    @Override
    public Bitmap toBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(DICE_WIDTH, DICE_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();

        Utils.drawBitmapOnCanvas(canvas, DICE_FRAME, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);

        int diceIndex = diceNumber;
        if(startDiceTime + MAX_DICE_TIME >= System.currentTimeMillis()) {
            diceIndex = throwing++ % 6;
        }

        Utils.drawBitmapOnCanvas(canvas, DICE_BMPS[diceIndex], paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);

        return bitmap;
    }

    private static Bitmap diceFrame() {
        Bitmap bitmap = Bitmap.createBitmap(DICE_WIDTH, DICE_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        Paint paint = new Paint();
        paint.setColor(Configuration.fontColor());
        paint.setShadowLayer(1, 0, 0, Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawLine(0, 0, DICE_WIDTH, 0, paint);
        canvas.drawLine(DICE_WIDTH, 0, DICE_WIDTH, DICE_WIDTH, paint);
        canvas.drawLine(DICE_WIDTH, DICE_WIDTH, 0, DICE_WIDTH, paint);
        canvas.drawLine(0, DICE_WIDTH, 0, 0, paint);

        return bitmap;
    }

    private static Bitmap dicePoint(int point) {
        Bitmap bitmap = Bitmap.createBitmap(DICE_WIDTH, DICE_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        Paint paint = new Paint();
        paint.setColor(Configuration.fontColor());

        int radius = pointRadius(point);

        switch (point) {
            case 0:
                canvas.drawCircle(DICE_WIDTH / 2, DICE_WIDTH / 2, radius, paint);
                break;
            case 1:
                canvas.drawCircle(DICE_WIDTH / 3, DICE_WIDTH / 3, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 2 / 3, DICE_WIDTH * 2 / 3, radius, paint);
                break;
            case 2:
                canvas.drawCircle(DICE_WIDTH * 7 / 24, DICE_WIDTH * 7 / 24, radius, paint);
                canvas.drawCircle(DICE_WIDTH / 2, DICE_WIDTH / 2, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 17 / 24, DICE_WIDTH * 17 / 24, radius, paint);
                break;
            case 3:
                canvas.drawCircle(DICE_WIDTH / 4, DICE_WIDTH / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH / 4, DICE_WIDTH * 3 / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 3 / 4, DICE_WIDTH / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 3 / 4, DICE_WIDTH * 3 / 4, radius, paint);
                break;
            case 4:
                canvas.drawCircle(DICE_WIDTH / 4, DICE_WIDTH / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH / 4, DICE_WIDTH * 3 / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH / 2, DICE_WIDTH / 2, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 3 / 4, DICE_WIDTH / 5, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 3 / 4, DICE_WIDTH * 3 / 4, radius, paint);
                break;
            default:
                canvas.drawCircle(DICE_WIDTH / 3, DICE_WIDTH / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH / 3, DICE_WIDTH / 2, radius, paint);
                canvas.drawCircle(DICE_WIDTH / 3, DICE_WIDTH * 3 / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 2 / 3, DICE_WIDTH / 4, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 2 / 3, DICE_WIDTH / 2, radius, paint);
                canvas.drawCircle(DICE_WIDTH * 2 / 3, DICE_WIDTH * 3 / 4, radius, paint);
        }

        return bitmap;
    }

    private static int pointRadius(int point) {
        switch (point) {
            case 0:
                return DICE_WIDTH / 4;
            case 1:
                return DICE_WIDTH / 6;
            case 2:
            case 3:
                return DICE_WIDTH / 8;
            default:
                return DICE_WIDTH / 9;
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
