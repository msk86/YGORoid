package android.ygo.core;

import android.graphics.Bitmap;

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
        cards.add(0, card);
    }

    public void unShift(Card card) {
        if(reserved) {
            card.open();
        } else {
            card.set();
        }
        cards.add(card);
    }


    @Override
    public Bitmap highLight() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bitmap toBitmap() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
