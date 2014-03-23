package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.impl.renderer.OverRayRenderer;

public class OverRay implements Item, Selectable, Controllable, Infoable, Listable {

    CardList overRayCards;

    public OverRay() {
        overRayCards = new CardList(true, true, null);
        overRayCards.setListTopCard(false);
    }

    public void overRay(Card card) {
        boolean isCardNegative = !card.isPositive();
        // Token can be used as a XYZ card.
        if(card.isXYZ() || card.isToken()) {
            changeOverRayUnitsPosition();
            overRayCards.push(card);
            if(isCardNegative) card.negative();
        } else {
            Card top = overRayCards.topCard();
            if(top != null && (top.isXYZ() || top.isToken())) {
                overRayCards.unShift(card);
            } else {
                changeOverRayUnitsPosition();
                overRayCards.push(card);
                if(isCardNegative) card.negative();
            }
        }
    }

    private void changeOverRayUnitsPosition() {
        for(Card overRayUnits : overRayCards.getCards()) {
            overRayUnits.positive();
            overRayUnits.open();
            overRayUnits.unSelect();
        }
    }

    public CardList getOverRayCards() {
        return overRayCards;
    }

    private boolean deepSelect = false;
    @Override
    public void select() {
        if(isSelect()) {
            deepSelect = true;
        }
        overRayCards.topCard().select();

    }

    @Override
    public void unSelect() {
        if(overRayCards.topCard() != null) {
            overRayCards.topCard().unSelect();
        }
        deepSelect = false;
    }

    @Override
    public boolean isSelect() {
        return overRayCards.topCard().isSelect();
    }

    public boolean isDeepSelect() {
        return deepSelect;
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
        return overRayCards;
    }
}
