package org.msk86.ygoroid.newcore.impl.side;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.layout.LinerLayout;
import org.msk86.ygoroid.newcore.impl.side.renderer.ExDeckSectionRenderer;
import org.msk86.ygoroid.newcore.impl.side.renderer.MainDeckSectionRenderer;

import java.util.List;

public class ExDeckSection implements Item, Container {
    DeckCards cards;
    List<Card> exDeck;

    public ExDeckSection(DeckCards cards) {
        this.cards = cards;
        exDeck = this.cards.getExDeckCards();
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new ExDeckSectionRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new LinerLayout(this, exDeck);
        }
        return layout;
    }
}
