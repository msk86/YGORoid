package org.msk86.ygoroid.newcore.deck;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.utils.Utils2;

import java.util.List;

public class DeckCards {
    String deckName;
    List<Card> mainDeckCards;
    List<Card> exDeckCards;
    List<Card> sideDeckCards;

    public DeckCards(String deckName) {
        this.deckName = deckName;
        List<List<Card>> lists = Utils2.getDbHelper().loadFromFile(deckName);
        mainDeckCards = lists.get(0);
        exDeckCards = lists.get(0);
        sideDeckCards = lists.get(1);
    }

    public String getDeckName() {
        return deckName;
    }

    public List<Card> getMainDeckCards() {
        return mainDeckCards;
    }

    public List<Card> getExDeckCards() {
        return exDeckCards;
    }

    public List<Card> getSideDeckCards() {
        return sideDeckCards;
    }
}
