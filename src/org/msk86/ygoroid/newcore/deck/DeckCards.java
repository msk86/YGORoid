package org.msk86.ygoroid.newcore.deck;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newutils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeckCards {
    String deckName;
    List<Card> mainDeckCards;
    List<Card> exDeckCards;
    List<Card> sideDeckCards;

    public DeckCards() {
        mainDeckCards = new ArrayList<Card>();
        exDeckCards = new ArrayList<Card>();
        sideDeckCards = new ArrayList<Card>();
    }

    public DeckCards(String deckName) {
        loadDeck(deckName);
    }

    public void loadDeck(String deckName) {
        this.deckName = deckName;
        List<List<Card>> lists = Utils.getDbHelper().loadFromFile(deckName);
        mainDeckCards = lists.get(0);
        exDeckCards = lists.get(1);
        sideDeckCards = lists.get(2);
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

    public List<Card> getDeckByCard(Card card) {
        if(mainDeckCards.contains(card)) return mainDeckCards;
        if(exDeckCards.contains(card)) return exDeckCards;
        if(sideDeckCards.contains(card)) return sideDeckCards;
        return null;
    }

    public List<Card> getAllCards() {
        List<Card> all = new ArrayList<Card>();
        all.addAll(mainDeckCards);
        all.addAll(exDeckCards);
        all.addAll(sideDeckCards);
        return all;
    }

    public boolean save() {
        if(deckName == null || deckName.length() == 0) {
            return false;
        }

        return Utils.getDbHelper().saveToFile(deckName, mainDeckCards, exDeckCards, sideDeckCards);
    }

    public boolean saveAs(String deckName) {
        this.deckName = deckName;
        boolean saved = save();
        if(!saved) {
            this.deckName = null;
        }
        return saved;
    }
}
