package org.msk86.ygoroid.newcore.impl.builder;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.deck.DeckChecker;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newcore.impl.InfoBar;
import org.msk86.ygoroid.newcore.impl.bmp.CardGenerator;
import org.msk86.ygoroid.newcore.impl.bmp.UserDefinedCardGenerator;
import org.msk86.ygoroid.newcore.impl.builder.renderer.SideChangerRenderer;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;

import java.util.ArrayList;
import java.util.List;

public class SideChanger implements Item, Container, BaseContainer {
    DeckCards cards;
    DeckChecker checker;
    List<Item> sections;

    public SideChanger() {
        sections = new ArrayList<Item>();
        sections.add(new MainDeckSection(this));
        sections.add(new ExDeckSection(this));
        sections.add(new SideDeckSection(this));
        sections.add(new InfoBar(this));
    }

    public void loadDeck(DeckCards cards) {
        this.cards = cards;
        checker = new DeckChecker(this.cards).startCheck();
        getMainDeckSection().setCards(cards);
        getExDeckSection().setCards(cards);
        getSideDeckSection().setCards(cards);
    }

    public DeckCards getCards() {
        return cards;
    }

    public MainDeckSection getMainDeckSection() {
        return (MainDeckSection) sections.get(0);
    }
    public ExDeckSection getExDeckSection() {
        return (ExDeckSection) sections.get(1);
    }
    public SideDeckSection getSideDeckSection() {
        return (SideDeckSection) sections.get(2);
    }
    public InfoBar getInfoBar() {
        return (InfoBar) sections.get(3);
    }

    Card currentSelectCard;

    public void unSelect() {
        if (currentSelectCard != null) {
            currentSelectCard.unSelect();
            currentSelectCard = null;
        }
        getInfoBar().clearInfo();
    }

    public void select(Card selectable) {
        if(selectable != null) {
            if(selectable != currentSelectCard) {
                unSelect();
            }
            currentSelectCard = selectable;
            currentSelectCard.select();
        }
        getInfoBar().setInfo(currentSelectCard);
    }

    public void recycleUselessBmp() {
        CardGenerator.clearCache();
        UserDefinedCardGenerator.clearCache();
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer  = new SideChangerRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if( layout==null) {
            layout = new AbsoluteLayout(this);
        }
        return layout;
    }

    CardEffectWindow cardEffectWindow;
    public void setCardEffectWindow(CardEffectWindow window) {
        cardEffectWindow = window;
    }

    public CardEffectWindow getCardEffectWindow() {
        return cardEffectWindow;
    }

    @Override
    public Selectable getCurrentSelectItem() {
        return currentSelectCard;
    }
}
