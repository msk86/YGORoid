package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Card implements Item {
    public static final String DEFAULT_CARD_PROTECTOR = "defaultProtector";

    String id;
    String name;
    String desc;
    CardType type;
    String cardProtector;
    boolean set = false;
    boolean positive = true;

    public Card(String id, CardType type, boolean positive) {
        this.id = id;
        this.type = type;
        this.positive = positive;
    }

    @Override
    public Bitmap toBitmap() {
        int height = Utils.cardHeight();
        int width = height;
        Bitmap cardBmp = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cardBmp);
        Paint paint = new Paint();
        if (set) {
            Bitmap protector = Utils.readBitmapScaleByHeight(cardProtector + ".png", height);
            int drawX = (width - protector.getWidth()) / 2;
            canvas.drawBitmap(protector, drawX, 0, paint);
        } else {
            if(Configuration.isTotalCardPic()) {
                Bitmap cardPic = Utils.readBitmapScaleByHeight(id + ".png", height / 2);
                int drawX = (width - cardPic.getWidth()) / 2;
                canvas.drawBitmap(cardPic, drawX, 0, paint);
            } else {
                Bitmap cardBackground = Utils.readBitmapScaleByHeight(type.toString() + ".png", height);
                int drawX = (width - cardBackground.getWidth()) / 2;
                canvas.drawBitmap(cardBackground, drawX, 0, paint);
                Bitmap cardPic = Utils.readBitmapScaleByHeight(id + ".png", height / 2);
                int cardPicX = (width - cardPic.getWidth()) / 2;
                int cardPicY = (int) (cardBackground.getHeight() / 4.63);
                canvas.drawBitmap(cardPic, cardPicX, cardPicY, paint);
            }
        }
        if (!positive) {
            cardBmp = Utils.rotate(cardBmp, 90);
        }
        return cardBmp;
    }
}
