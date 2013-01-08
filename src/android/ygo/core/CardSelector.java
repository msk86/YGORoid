package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class CardSelector implements Item {

    SelectableItem sourceItem;
    CardList cardList;

    private int pageLimit = 21;
    private int page = 0;

    public CardSelector(SelectableItem sourceItem, CardList cardList) {
        this.sourceItem = sourceItem;
        this.cardList = cardList;
    }

    public Card cardAt(int x, int y) {
        return null;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap background = background();
        int cardPadding = 5;
        int border = 15;
        Bitmap bmp = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        Utils.drawBitmapOnCanvas(canvas, background, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);

        int x = border;
        int y = border;
        for(int i=page * pageLimit;i<cardList.size();i++) {
            if(i == (page + 1) * pageLimit) {
                break;
            }
            Card card = cardList.cards.get(i);
            if(i != 0 && i % 7 == 0) {
                x = border;
                y += Utils.cardHeight() + cardPadding;
            }
            x += cardPadding;

            Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, x, y);

            x += Utils.cardWidth();
        }

        return bmp;
    }

    public Bitmap background() {
        int cardPadding = 5;
        int border = 15;
        int width = Utils.unitLength() * 6;
        // border * 2 + Utils.cardWidth() * 7 + cp * 8
        int height = border * 2 + Utils.cardHeight() * 3 + cardPadding * 4;
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        canvas.drawColor(Color.GRAY);
        return background;
    }
}
