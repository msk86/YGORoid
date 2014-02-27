package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.renderer.DeckRenderer;

public class Deck implements Item, Selectable {
    String name;
    CardList cardList;

    public Deck(String name, boolean open) {
        this.name = name;
        cardList = new CardList(open);
    }

    public String getName() {
        return name;
    }

    public CardList getCardList() {
        return cardList;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new DeckRenderer(this);
        }
        return renderer;
    }


    boolean select;
    @Override
    public void select() {
        select = true;
    }

    @Override
    public void unSelect() {
        select = false;
    }

    @Override
    public boolean isSelect() {
        return select;
    }
}
