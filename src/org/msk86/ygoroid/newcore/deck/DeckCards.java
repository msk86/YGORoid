package org.msk86.ygoroid.newcore.deck;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardList;
import org.msk86.ygoroid.newutils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeckCards {
    String deckName;
    CardList mainDeckCards;
    CardList exDeckCards;
    CardList sideDeckCards;

    public DeckCards() {
        mainDeckCards = new CardList();
        exDeckCards = new CardList();
        sideDeckCards = new CardList();
    }

    public DeckCards(String deckName) {
        this();
        loadDeck(deckName);
    }

    public void loadDeck(String deckName) {
        this.deckName = deckName;
        List<List<Card>> lists = Utils.getDbHelper().loadFromFile(deckName);
        mainDeckCards.unshiftAll(lists.get(0));
        exDeckCards.unshiftAll(lists.get(1));
        sideDeckCards.unshiftAll(lists.get(2));
    }

    public String getDeckName() {
        return deckName;
    }

    public CardList getMainDeckCards() {
        return mainDeckCards;
    }

    public CardList getExDeckCards() {
        return exDeckCards;
    }

    public CardList getSideDeckCards() {
        return sideDeckCards;
    }

    public CardList getDeckByCard(Card card) {
        if(mainDeckCards.getCards().contains(card)) return mainDeckCards;
        if(exDeckCards.getCards().contains(card)) return exDeckCards;
        if(sideDeckCards.getCards().contains(card)) return sideDeckCards;
        return null;
    }

    public List<Card> getAllCards() {
        List<Card> all = new ArrayList<Card>();
        all.addAll(mainDeckCards.getCards());
        all.addAll(exDeckCards.getCards());
        all.addAll(sideDeckCards.getCards());
        return all;
    }

    public boolean save() {
        if(deckName == null || deckName.length() == 0) {
            return false;
        }

        return Utils.getDbHelper().saveToFile(deckName, mainDeckCards.getCards(), exDeckCards.getCards(), sideDeckCards.getCards());
    }

    public boolean saveAs(String deckName) {
        this.deckName = deckName;
        boolean saved = save();
        if(!saved) {
            this.deckName = null;
        }
        return saved;
    }

    public void delete() {
        if(deckName != null) {
            Utils.deleteDeck(deckName);
        }
        deckName = null;
        mainDeckCards = new CardList();
        exDeckCards = new CardList();
        sideDeckCards = new CardList();
    }
}
