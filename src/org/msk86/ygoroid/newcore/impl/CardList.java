package org.msk86.ygoroid.newcore.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CardList {
    boolean open, canStoreToken, listTopCard;
    List<Card> cards;

    public CardList() {
        this(true);
    }
    public CardList(boolean open) {
        this(open, new CopyOnWriteArrayList<Card>());
    }

    public CardList(boolean open, List<Card> cards) {
        this(open, false, cards);
    }

    public CardList(boolean open, boolean canStoreToken, List<Card> cards) {
        this.open = open;
        this.canStoreToken = canStoreToken;
        this.listTopCard = true;
        this.cards = new CopyOnWriteArrayList<Card>();
        if(cards != null) {
            for (Card card : cards) {
                if(!canStoreToken && card.isToken()) {
                    continue;
                }
                if (open) {
                    card.open();
                } else {
                    card.set();
                }
                this.push(card);
            }
        }
    }

    public int size() {
        return cards.size();
    }

    public Card topCard() {
        if(size() > 0) {
            return cards.get(0);
        }
        return null;
    }

    public Card pop() {
        if(size() > 0) {
            return cards.remove(0);
        }
        return null;
    }

    public List<Card> pop(int size) {
        List<Card> cards = new CopyOnWriteArrayList<Card>();
        for (int i = 0; i < size; i++) {
            Card card = pop();
            if (card != null) {
                cards.add(card);
            }
        }
        return cards;
    }

    public Card remove(Card card) {
        if(cards.remove(card)) {
            return card;
        }
        return null;
    }

    public void push(Card card) {
        push(card, false);
    }

    public void push(Card card, boolean force) {
        add(card, 0, force);
    }

    public void push(List<Card> cards) {
        for (Card card : cards) {
            this.push(card);
        }
    }

    public void push(CardList list) {
        this.push(list.cards);
    }

    public void unShift(Card card) {
        add(card, cards.size(), false);
    }


    private void add(Card card, int index, boolean force) {
        if (card == null) {
            return;
        }
        if (!canStoreToken && card.isToken()) {
            return;
        }
        if(!force) {
            if(open) {
                card.open();
            } else {
                card.set();
            }
        }
        card.positive();
        card.unSelect();
        card.getIndicator().clear();
        cards.add(index, card);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void openAll() {
        for (Card card : cards) {
            card.open();
        }
    }

    public void setAll() {
        for (Card card : cards) {
            card.set();
        }
    }

    public void reverse() {
        List<Card> reservedCards = new CopyOnWriteArrayList<Card>();
        for (int i = cards.size() - 1; i >= 0; i--) {
            Card card = cards.get(i);
            card.flip();
            reservedCards.add(card);
        }
        cards = reservedCards;
        this.open = !this.open;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setListTopCard(boolean listTopCard) {
        this.listTopCard = listTopCard;
    }
}
