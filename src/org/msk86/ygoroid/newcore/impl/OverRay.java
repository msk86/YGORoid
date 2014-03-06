package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.impl.renderer.OverRayRenderer;

public class OverRay implements Item, Selectable, Controllable, Infoable, Listable {

    CardList overRayCards;

    public OverRay() {
        overRayCards = new CardList(true, true, null);
    }

    public void overRay(Card card) {
        // Token can be used as a XYZ card.
        if(card.isXYZ() || card.isToken()) {
            overRayCards.push(card);
        } else {
            Card top = overRayCards.topCard();
            if(top != null && (top.isXYZ() || top.isToken())) {
                overRayCards.unShift(card);
            } else {
                overRayCards.push(card);
            }
        }
    }

    public CardList getOverRayCards() {
        return overRayCards;
    }

    @Override
    public void select() {
        overRayCards.topCard().select();
    }

    @Override
    public void unSelect() {
        overRayCards.topCard().unSelect();
    }

    @Override
    public boolean isSelect() {
        return overRayCards.topCard().isSelect();
    }

    private Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new OverRayRenderer(this);
        }
        return renderer;
    }

    @Override
    public void flip() {
        overRayCards.topCard().flip();
    }

    @Override
    public void open() {
        overRayCards.topCard().open();
    }

    @Override
    public void set() {
        overRayCards.topCard().set();
    }

    @Override
    public boolean isOpen() {
        return overRayCards.topCard().isOpen();
    }

    @Override
    public void rotate() {
        overRayCards.topCard().rotate();
    }

    @Override
    public void positive() {
        overRayCards.topCard().positive();
    }

    @Override
    public void negative() {
        overRayCards.topCard().negative();
    }

    @Override
    public boolean isPositive() {
        return overRayCards.topCard().isPositive();
    }

    @Override
    public String getInfo() {
        return overRayCards.topCard().getInfo();
    }

    @Override
    public CardList listCards() {
        return new CardList(overRayCards.isOpen(), true, overRayCards.getCards().subList(1, overRayCards.size()));
    }
}
