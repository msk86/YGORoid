package org.msk86.ygoroid.newcore.impl.builder;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.layout.LinerLayout;
import org.msk86.ygoroid.newcore.impl.builder.renderer.SideDeckSectionRenderer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SideDeckSection implements Item, Container {
    DeckCards cards;
    List<Card> sideDeck;
    Item holder;

    public SideDeckSection(Item holder) {
        sideDeck = new CopyOnWriteArrayList<Card>();
        this.holder = holder;
    }

    public void setCards(DeckCards cards) {
        this.cards = cards;
        sideDeck = this.cards.getSideDeckCards();
    }

    public Item getHolder() {
        return holder;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new SideDeckSectionRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new LinerLayout(this, sideDeck);
        }
        return layout;
    }
}
