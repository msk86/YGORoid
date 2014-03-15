package org.msk86.ygoroid.newcore.impl.side;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.layout.GridLayout;
import org.msk86.ygoroid.newcore.impl.side.renderer.MainDeckSectionRenderer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainDeckSection implements Item, Container {
    DeckCards cards;
    List<Card> mainDeck;
    Item holder;

    public MainDeckSection(Item holder) {
        mainDeck = new CopyOnWriteArrayList<Card>();
        this.holder = holder;
    }

    public void setCards(DeckCards cards) {
        this.cards = cards;
        mainDeck = this.cards.getMainDeckCards();
    }

    public Item getHolder() {
        return holder;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new MainDeckSectionRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new GridLayout(this, mainDeck);
        }
        return layout;
    }
}
