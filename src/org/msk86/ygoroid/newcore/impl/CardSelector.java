package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.layout.GridLayout;
import org.msk86.ygoroid.newcore.impl.renderer.CardSelectorRenderer;

public class CardSelector implements Item, Container {
    Item source;
    CardList cardList;

    public CardSelector(Item source, CardList cardList) {
        this.source = source;
        this.cardList = cardList;
    }

    public Item getSource() {
        return source;
    }

    public CardList getCardList() {
        return cardList;
    }

    private Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new GridLayout(this, cardList.getCards());
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
