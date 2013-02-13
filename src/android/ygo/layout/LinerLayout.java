package android.ygo.layout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.core.Card;
import android.ygo.utils.Utils;

import java.util.List;

public class LinerLayout {
    List<Card> cards;

    int maxWidth;
    int cardPadding;
    int realWidth;

    public LinerLayout(List<Card> cards, int maxWidth) {
        this.maxWidth = maxWidth;
        this.cards = cards;
    }

    public void fixPosition() {
        int maxPadding = Utils.cardWidth() / 10;
        if (cards.size() > 1) {
            cardPadding = (maxWidth - Utils.cardWidth()) / (cards.size() - 1) - Utils.cardWidth();
            cardPadding = cardPadding < maxPadding ? cardPadding : maxPadding;
        } else if (cards.size() == 1) {
            cardPadding = maxPadding;
        } else {
            cardPadding = 0;
        }

        realWidth = cards.size() * Utils.cardWidth() + (cards.size() - 1) * cardPadding + 1;
    }

    public Bitmap toBitmap() {
        fixPosition();
        Bitmap bmp = Bitmap.createBitmap(realWidth, Utils.cardHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        int posX = 0;
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, posX, 0);
            posX += Utils.cardWidth() + cardPadding;
        }
        return bmp;
    }

    public Card cardAt(int x, int y) {
        fixPosition();
        if (x < 0 || x >= realWidth) {
            return null;
        }

        int index = x / (Utils.cardWidth() + cardPadding);
        index = index < cards.size() ? index : cards.size() - 1;
        return cards.get(index);
    }
}
