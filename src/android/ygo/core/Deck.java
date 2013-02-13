package android.ygo.core;

import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Deck extends CardList {

    public Deck(String name) {
        super(name, false);
    }

    public Deck(String name, List<Card> cards) {
        super(name, cards, false);
    }

    public void reserve() {
        List<Card> reservedCards = new ArrayList<Card>();
        for (int i = cards.size() - 1; i >= 0; i--) {
            Card card = cards.get(i);
            card.flip();
            reservedCards.add(card);
        }
        cards = reservedCards;
        super.open = !super.open;
    }
}
