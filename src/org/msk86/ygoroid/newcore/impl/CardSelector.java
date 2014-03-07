package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.impl.layout.GridLayout;
import org.msk86.ygoroid.newcore.impl.renderer.CardSelectorRenderer;

import java.util.List;

public class CardSelector implements Item, Container {
    Listable source;
    CardList cardList;

    public CardSelector(Listable source, CardList cardList) {
        this.source = source;
        this.cardList = cardList;
    }

    public Listable getSource() {
        return source;
    }

    public CardList getCardList() {
        return cardList;
    }

    private Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            List<Card> cardsInLayout = cardList.getCards();
            if(!cardList.listTopCard && cardList.size() >= 1) {
                cardsInLayout = cardList.getCards().subList(1, cardList.size());
            }
            layout = new GridLayout(this, cardsInLayout);
        }
        return layout;
    }

    private Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new CardSelectorRenderer(this);
        }
        return renderer;
    }
}
