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

public class MainDeckSection implements Item, Container {
    DeckCards cards;
    List<Card> mainDeck;

    public MainDeckSection(DeckCards cards) {
        this.cards = cards;
        mainDeck = this.cards.getMainDeckCards();
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
