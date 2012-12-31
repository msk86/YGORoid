package android.ygo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck extends CardList {

    public Deck(String name) {
        super(name, false);
    }

    public Deck(String name, List<Card> cards) {
        super(name, cards, false);
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
        super.open = !super.open;
    }
}
