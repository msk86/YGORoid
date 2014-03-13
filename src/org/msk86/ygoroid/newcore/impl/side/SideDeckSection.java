package org.msk86.ygoroid.newcore.impl.side;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.layout.LinerLayout;
import org.msk86.ygoroid.newcore.impl.side.renderer.SideDeckSectionRenderer;

import java.util.List;

public class SideDeckSection implements Item, Container {
    DeckCards cards;
    List<Card> sideDeck;

    public SideDeckSection(DeckCards cards) {
        this.cards = cards;
        sideDeck = this.cards.getSideDeckCards();
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
