package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.layout.LinerLayout;
import org.msk86.ygoroid.newcore.impl.renderer.HandCardsRenderer;
import org.msk86.ygoroid.size.CardSize;

import java.util.List;

public class HandCards implements Item, Container {
    CardList cardList;

    public HandCards() {
        cardList = new CardList(false, false, null);
    }

    public void add(Card card) {
        cardList.unShift(card);
    }

    public void add(List<Card> cards) {
        for(Card card : cards) {
            add(card);
        }
    }

    public CardList getCardList() {
        return cardList;
    }


    private Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new LinerLayout(this, this.cardList.getCards())
                    .setCenterAligned(true)
                    .setMaxPadding(CardSize.NORMAL.width() / 10);
        }
        return layout;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new HandCardsRenderer(this);
        }
        return renderer;
    }
}
