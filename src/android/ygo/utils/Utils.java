package android.ygo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.ygo.core.Card;
import android.ygo.sqlite.CardsDBHelper;

import java.util.List;
import java.util.Random;

public class Utils {
    private static DisplayMetrics dm;
    private static Activity context;
    private static CardsDBHelper dbHelper;

    public static void initInstance(Activity activity) {
        context = activity;
        dm = new DisplayMetrics();
        dbHelper = new CardsDBHelper(activity, 1);
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public static Activity getContext() {
        return context;
    }

    public static CardsDBHelper getDbHelper() {
        return dbHelper;
    }

    public static int screenHeight() {
        return dm.heightPixels;
    }

    public static int screenWidth() {
        return dm.widthPixels;
    }

    public static int unitLength() {
        int longer = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
        int shorter = dm.widthPixels < dm.heightPixels ? dm.widthPixels : dm.heightPixels;
        int unitLengthW = (int) (longer / 6f);
        int unitLengthH = (int) (shorter / 3.95f);
        return unitLengthW < unitLengthH ? unitLengthW : unitLengthH;
    }

    public static int totalWidth() {
        return unitLength() * 6;
    }

    public static int cardHeight() {
        int padding = 2;
        return unitLength() - padding * 2;
    }

    public static int cardWidth() {
        return (int) (cardHeight() / 1.45);
    }

    public static Bitmap readBitmapScaleByHeight(int resId, int targetHeight) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return scaleByHeight(bitmap, targetHeight);
    }

    public static Bitmap readBitmapScaleByHeight(String file, int targetHeight) {
        Bitmap bitmap = BitmapFactory.decodeFile(file);
        return scaleByHeight(bitmap, targetHeight);
    }

    public static Bitmap scaleByHeight(Bitmap bitmap, int targetHeight) {
        Matrix matrix = new Matrix();

        float changeRate = targetHeight * 1.0f / bitmap.getHeight();
        matrix.postScale(changeRate, changeRate);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newBitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postRotate(degree);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newBitmap;
    }

    public static final int DRAW_POSITION_FIRST = -0x1000;
    public static final int DRAW_POSITION_CENTER = -0x2000;
    public static final int DRAW_POSITION_LAST = -0x3000;

    public static void drawBitmapOnCanvas(Canvas canvas, Bitmap bitmap, Paint paint, int positionX, int positionY) {
        int posX = 0;
        int posY = 0;
        switch (positionX) {
            case DRAW_POSITION_FIRST:
                posX = 0;
                break;
            case DRAW_POSITION_CENTER:
                posX = (canvas.getWidth() - bitmap.getWidth()) / 2;
                break;
            case DRAW_POSITION_LAST:
                posX = canvas.getWidth() - bitmap.getWidth();
                break;
            default:
                posX = positionX;
        }
        switch (positionY) {
            case DRAW_POSITION_FIRST:
                posY = 0;
                break;
            case DRAW_POSITION_CENTER:
                posY = (canvas.getHeight() - bitmap.getHeight()) / 2;
                break;
            case DRAW_POSITION_LAST:
                posY = canvas.getHeight() - bitmap.getHeight();
                break;
            default:
                posY = positionY;
        }
        canvas.drawBitmap(bitmap, posX, posY, paint);
    }


    public static void shuffle(List<Card> cards) {
        Random random = new Random();
        for (int i = 1; i < cards.size(); i++) {
            swapCard(cards, i, random.nextInt(i));
        }
    }

    private static void swapCard(List<Card> cards, int indexA, int indexB) {
        Card temp = cards.get(indexA);
        cards.set(indexA, cards.get(indexB));
        cards.set(indexB, temp);
    }

    public static int textPlace(char c) {
        if (c < 32) {
            return 0;
        }
        if (c < 256) {
            return 1;
        }
        return 2;
    }

}
