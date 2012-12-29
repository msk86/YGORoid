package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck implements SelectableItem {
    List<Card> cards;
    boolean reserved = false;

    public Deck(List<String> cardIds) {
        cards = new ArrayList<Card>();
        for (String id : cardIds) {
            cards.add(new Card(id));
        }
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = 1; i < cards.size(); i++) {
            swapCard(i, random.nextInt(i));
        }
    }

    private void swapCard(int indexA, int indexB) {
        Card temp = cards.get(indexA);
        cards.set(indexA, cards.get(indexB));
        cards.set(indexB, temp);
    }

    public void reserve() {
        List<Card> reservedCards = new ArrayList<Card>();
        for (int i = cards.size() - 1; i >= 0; i--) {
            Card card = cards.get(i);
            card.turnOver();
            reservedCards.add(card);
        }
        cards = reservedCards;
        reserved = !reserved;
    }

    public Card pop() {
        return cards.remove(0);
    }

    public void push(Card card) {
        if(reserved) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        cards.add(0, card);
    }

    public void unShift(Card card) {
        if(reserved) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        cards.add(card);
    }


    @Override
    public Bitmap highLight() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
        Bitmap highLightBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(highLightBmp);
        Paint paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(width, 0, width, height, paint);
        canvas.drawLine(width, height, 0, height, paint);
        canvas.drawLine(0, height, 0, 0, paint);
        return highLightBmp;
    }

    @Override
    public Bitmap toBitmap() {
        if(cards.size() > 0) {
            return cards.get(0).toBitmap();
        }
        Bitmap deckBmp = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(deckBmp);
        canvas.drawColor(Color.TRANSPARENT);
        return deckBmp;
    }
}
