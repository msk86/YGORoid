package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HandCards implements Item {

    private List<Card> cards;

    public HandCards() {
        cards = new ArrayList<Card>();
    }

    public void shuffle() {
        Utils.shuffle(cards);
    }

    public void add(Card card) {
        card.open();
        card.positive();
        cards.add(card);
    }

    public void add(List<Card> cards) {
        for(Card card : cards) {
            add(card);
        }
    }

    public Card remove(Card card) {
        if(cards.remove(card)) {
            return card;
        }
        return null;
    }

    public int size() {
        return cards.size();
    }


    private Bitmap cardsBmp() {
        int maxWidth = Utils.unitLength() * 6;
        int maxPadding = Utils.cardWidth() / 10;
        int wPadding = (maxWidth - Utils.cardWidth()) / (cards.size() - 1) - Utils.cardWidth();

        wPadding = wPadding < maxPadding ? wPadding : maxPadding;

        int width = cards.size() * Utils.cardWidth() + (cards.size() - 1) * wPadding;
        int height = Utils.cardHeight();

        Bitmap handCardBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(handCardBmp);
        Paint paint = new Paint();

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
            canvas.translate(Utils.cardWidth(), 0);
            canvas.translate(wPadding, 0);
        }

        return handCardBmp;
    }

    @Override
    public Bitmap toBitmap() {
        int width = Utils.unitLength() * 6;
        int height = Utils.cardHeight();

        Bitmap handCardBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(handCardBmp);
        Paint paint = new Paint();

        Bitmap cardsBmp = cardsBmp();

        Utils.drawBitmapOnCanvas(canvas, cardsBmp, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);

        cardsBmp.recycle();

        return handCardBmp;
    }
}
