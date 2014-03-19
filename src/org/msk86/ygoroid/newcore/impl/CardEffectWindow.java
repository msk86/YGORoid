package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.CardEffectWindowRenderer;

public class CardEffectWindow implements Item {
    Item windowHolder;
    Card card;

    private int effectTextPage = 0;

    public CardEffectWindow(Item windowHolder, Card card) {
        this.windowHolder = windowHolder;
        this.card = card;
    }

    public void nextPage() {
        effectTextPage ++;
    }

    public Item getWindowHolder() {
        return windowHolder;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        effectTextPage = 0;
    }

    public int getEffectTextPage() {
        return effectTextPage;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer==null) {
            renderer = new CardEffectWindowRenderer(this);
        }
        return renderer;
    }
}
