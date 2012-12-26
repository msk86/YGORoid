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

    public Card(String id, CardType type) {
        this.id = id;
        this.type = type;
    }


    @Override
    public Bitmap toBitmap() {
        int height = Utils.cardHeight();
        Bitmap cardBmp = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cardBmp);
        Paint paint = new Paint();
        if(set) {
            Bitmap protector = Utils.readBitmapScaleByHeight(cardProtector + ".png", height);
            canvas.drawBitmap(protector, 0, 0, paint);
        } else {
            Bitmap cardBackground = Utils.readBitmapScaleByHeight(type.toString() + ".png", height);
            canvas.drawBitmap(cardBackground, 0, 0, paint);
            Bitmap cardPic = Utils.readBitmapScaleByHeight(id + ".png", height / 2);

            int cardPicX = (cardBackground.getWidth() - cardPic.getWidth()) / 2;
            int cardPicY = cardBackground.getHeight() / 5;

            canvas.drawBitmap(cardPic, cardPicX, cardPicY, paint);
        }
        if(!positive) {
            canvas.rotate(90);
        }
        return cardBmp;
    }
}
