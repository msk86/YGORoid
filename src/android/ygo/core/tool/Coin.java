package android.ygo.core.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.R;
import android.ygo.core.SelectableItem;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.Random;

public class Coin implements SelectableItem {
    private static int COIN_RADIUS = Utils.unitLength() / 4;
    private static int COIN_INNER_RADIUS = Utils.unitLength() / 5;
    private static Bitmap[] COIN_BMPS = new Bitmap[2];
    private static Bitmap COIN_FRAME;
    private static Bitmap MASK;
    private static int MAX_THROWING = 8;

    static {
        for (int i = 0; i < COIN_BMPS.length; i++) {
            COIN_BMPS[i] = coinBmp(i);
        }
        COIN_FRAME = coinFrame();
        MASK = Utils.readBitmapScaleByHeight(R.raw.mask, COIN_RADIUS * 2);
    }

    private int coinNumber;

    private int throwing;
    private Random random;

    public Coin() {
        random = new Random();
        coinNumber = 1;
        throwing = MAX_THROWING + 1;
    }

    public void throwCoin() {
        throwing = 1;
        coinNumber = random.nextInt(2);
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(COIN_RADIUS * 2, COIN_RADIUS * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();

        Utils.drawBitmapOnCanvas(canvas, COIN_FRAME, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);

        Utils.drawBitmapOnCanvas(canvas, COIN_BMPS[coinNumber], paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);

        drawMask(canvas, paint);

        return bitmap;
    }

    private static Bitmap coinBmp(int coin) {
        Bitmap bitmap = Bitmap.createBitmap(COIN_RADIUS * 2, COIN_RADIUS * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(COIN_RADIUS / 3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        canvas.drawLine(COIN_RADIUS / 3, COIN_RADIUS, COIN_RADIUS * 5 / 3, COIN_RADIUS, paint);
        if (coin == 1) {
            canvas.drawLine(COIN_RADIUS, COIN_RADIUS / 3, COIN_RADIUS, COIN_RADIUS * 5 / 3, paint);
        }
        return bitmap;
    }

    private void drawMask(Canvas canvas, Paint paint) {
        if (throwing <= MAX_THROWING) {
            int stepWidth = (int) Math.ceil(COIN_RADIUS * 4.0 / MAX_THROWING);
            Utils.drawBitmapOnCanvas(canvas, MASK, paint, -COIN_RADIUS * 2 + stepWidth * throwing, Utils.DRAW_POSITION_FIRST);
            throwing++;
        }
    }

    private static Bitmap coinFrame() {
        Bitmap bitmap = Bitmap.createBitmap(COIN_RADIUS * 2, COIN_RADIUS * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int padding = 2;
        canvas.drawCircle(COIN_RADIUS, COIN_RADIUS, COIN_RADIUS - padding, paint);
        paint.setStrokeWidth(2);
        canvas.drawCircle(COIN_RADIUS, COIN_RADIUS, COIN_INNER_RADIUS - padding, paint);

        return bitmap;
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
