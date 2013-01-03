package android.ygo.core;

import java.util.List;

public class TempList extends CardList{

    public TempList(String name) {
        super(name);
    }

    public void push(Card card) {
        if (card == null) {
            return;
        }
        card.positive();
        card.unSelect();
        cards.add(0, card);
    }

    public void push(List<Card> cards) {
        for (Card card : cards) {
            this.push(card);
        }
    }

    public void push(TempList list) {
        this.push(list.cards);
    }


    public void unShift(Card card) {
        card.positive();
        card.unSelect();
        cards.add(card);
    }

}
