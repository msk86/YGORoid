package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HandCards implements Item {

    private List<Card> cards;
    private boolean set;

    public HandCards() {
        cards = new ArrayList<Card>();
        set = false;
    }

    public void shuffle() {
        Utils.shuffle(cards);
    }

    public void add(Card card) {
        if(!set) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        cards.add(card);
    }

    public void add(List<Card> cards) {
        for (Card card : cards) {
            add(card);
        }
    }

    public Card remove(Card card) {
        if (cards.remove(card)) {
            return card;
        }
        return null;
    }

    public int size() {
        return cards.size();
    }

    public Card cardAt(int x, int y) {
        int padding = (Utils.unitLength() * 6 - cardsWidth() ) / 2;
        if(x <= padding) {
            return null;
        }

        int cardPadding = cardPadding();

        int currX = padding;
        int cw = Utils.cardWidth();
        for(int i=0;i<cards.size();i++) {
            if(i == cards.size() - 1) {
                cardPadding = 0;
            }
            if(currX < x && x < currX + cw + cardPadding) {
                return cards.get(i);
            }
            currX += cw + cardPadding;
        }
        return null;
    }

    private int cardPadding() {
        if(cards.size() <= 1) {
            return 0;
        }
        int maxWidth = Utils.unitLength() * 6;
        int maxPadding = Utils.cardWidth() / 10;
        int wPadding = (maxWidth - Utils.cardWidth()) / (cards.size() - 1) - Utils.cardWidth();
        wPadding = wPadding < maxPadding ? wPadding : maxPadding;
        return wPadding;
    }

    private int cardsWidth() {
        int wPadding = cardPadding();
        return  cards.size() * Utils.cardWidth() + (cards.size() - 1) * wPadding + 1;
    }

    public void setAll() {
        for(Card card : cards) {
            card.set();
        }
        set = true;
    }

    public void openAll() {
        for(Card card : cards) {
            card.open();
        }
        set = false;
    }

    public void flipAll() {
        if(set) {
            openAll();
        } else {
            setAll();
        }
    }

    private Bitmap cardsBmp() {
        int wPadding = cardPadding();
        int hPadding = Utils.cardHeight() / 10;

        int width = cardsWidth();
        int height = Utils.cardHeight() + hPadding;

        Bitmap handCardBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(handCardBmp);
        Paint paint = new Paint();

        int posX = 0;
        int posY = 0;
        for (int i = 0; i < cards.size(); i++) {
            posY = hPadding;
            Card card = cards.get(i);
            if (card.isSelect()) {
                posY = 0;
            }
            Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, posX, posY);
            posX += Utils.cardWidth() + wPadding;
        }

        return handCardBmp;
    }

    @Override
    public Bitmap toBitmap() {
        int width = Utils.unitLength() * 6;
        int hPadding = Utils.cardHeight() / 10;
        int height = Utils.cardHeight() + hPadding;

        Bitmap handCardBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(handCardBmp);
        Paint paint = new Paint();

        Bitmap cardsBmp = cardsBmp();

        Utils.drawBitmapOnCanvas(canvas, cardsBmp, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);

        cardsBmp.recycle();

        return handCardBmp;
    }
}
