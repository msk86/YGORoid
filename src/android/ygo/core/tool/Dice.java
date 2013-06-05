package android.ygo.core.tool;

import android.graphics.*;
import android.ygo.R;
import android.ygo.core.SelectableItem;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.Random;

public class Dice implements SelectableItem {
    private static int DICE_WIDTH = Utils.unitLength() / 2;
    private static Bitmap[] DICE_BMPS = new Bitmap[6];
    private static Bitmap DICE_FRAME;
    private static Bitmap MASK;
    private static int MAX_THROWING = 8;

    static {
        for (int i = 0; i < DICE_BMPS.length; i++) {
            DICE_BMPS[i] = diceBmp(i);
        }
        DICE_FRAME = diceFrame();
        MASK = Utils.readBitmapScaleByHeight(R.raw.mask, DICE_WIDTH);
    }

    private int diceNumber;

    private int throwing;
    private Random random;

    public Dice() {
        random = new Random();
        diceNumber = 5;
        throwing = MAX_THROWING + 1;
    }

    public void throwDice() {
        throwing = 1;
        diceNumber = random.nextInt(6);
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(DICE_WIDTH, DICE_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();

        Utils.drawBitmapOnCanvas(canvas, DICE_FRAME, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);

        Utils.drawBitmapOnCanvas(canvas, DICE_BMPS[diceNumber], paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);

        drawMask(canvas, paint);

        return bitmap;
    }

    private void drawMask(Canvas canvas, Paint paint) {
        if (throwing <= MAX_THROWING) {
            int stepWidth = (int) Math.ceil(DICE_WIDTH * 2.0 / MAX_THROWING);
            Utils.drawBitmapOnCanvas(canvas, MASK, paint, -DICE_WIDTH + stepWidth * throwing, Utils.DRAW_POSITION_FIRST);
            throwing++;
        }
    }

    private static Bitmap diceFrame() {
        Bitmap bitmap = Bitmap.createBitmap(DICE_WIDTH, DICE_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int padding = 2;
        canvas.drawRoundRect(new RectF(padding, padding, DICE_WIDTH - padding, DICE_WIDTH - padding),
                7, 7, paint);

        return bitmap;
    }

    private static Bitmap diceBmp(int point) {
        Bitmap bitmap = Bitmap.createBitmap(DICE_WIDTH, DICE_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setAntiAlias(true);

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
