package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Card implements SelectableItem {
    public static final String DEFAULT_CARD_PROTECTOR = "defaultProtector";

    String id;
    String name;
    String desc;
    CardType type;
    String cardProtector;
    boolean set = false;
    boolean positive = true;

    public Card(String id) {
        this.id = id;
        this.type = CardType.NORMAL_MONSTER;
        this.set = true;
        cardProtector = DEFAULT_CARD_PROTECTOR;
    }


    public Card(String id, CardType type, boolean positive, boolean set) {
        this.id = id;
        this.type = type;
        this.positive = positive;
        this.set = set;
        cardProtector = DEFAULT_CARD_PROTECTOR;
    }

    public void turnOver() {
        set = !set;
    }

    public void open() {
        set = false;
    }

    public void set() {
        set = true;
    }

    @Override
    public Bitmap toBitmap() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
        Bitmap cardBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cardBmp);
        Paint paint = new Paint();
        if (set) {
            Bitmap protector = Utils.readBitmapScaleByHeight(cardProtector + ".png", height);
            Utils.drawBitmapOnCanvas(canvas, protector, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
            protector.recycle();
        } else {
            if (Configuration.isTotalCardPic()) {
                Bitmap cardPic = Utils.readBitmapScaleByHeight(id + ".png", height / 2);
                Utils.drawBitmapOnCanvas(canvas, cardPic, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
                cardPic.recycle();
            } else {
                Bitmap cardBackground = Utils.readBitmapScaleByHeight(type.toString() + ".png", height);
                Utils.drawBitmapOnCanvas(canvas, cardBackground, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
                cardBackground.recycle();
                Bitmap cardPic = Utils.readBitmapScaleByHeight(id + ".png", height / 2);
                int cardPicY = (int) (cardBackground.getHeight() / 4.63);
                Utils.drawBitmapOnCanvas(canvas, cardPic, paint, Utils.DRAW_POSITION_CENTER, cardPicY);
                cardPic.recycle();
            }
        }
        if (!positive) {
            cardBmp = Utils.rotate(cardBmp, 90);
        }
        return cardBmp;
    }

    @Override
    public Bitmap highLight() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
        Bitmap highLightBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(highLightBmp);
        Paint paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(width, 0, width, height, paint);
        canvas.drawLine(width, height, 0, height, paint);
        canvas.drawLine(0, height, 0, 0, paint);

        if (!positive) {
            highLightBmp = Utils.rotate(highLightBmp, 90);
        }
        return highLightBmp;
    }
}
