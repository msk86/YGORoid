package org.msk86.ygoroid.newcore.impl;

import android.widget.Toast;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.deck.DeckChecker;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.LifePointCalculator;
import org.msk86.ygoroid.newcore.impl.renderer.DuelRenderer;
import org.msk86.ygoroid.newop.Drag;
import org.msk86.ygoroid.newutils.Utils;

public class Duel implements Item, Container {
    private LifePoint lifePoint;
    private LifePointCalculator lifePointCalculator;
    private DuelFields duelFields;
    private HandCards handCards;
    private CardSelector cardSelector;
    private InfoBar infoBar;
    private CardEffectWindow cardEffectWindow;
    private Dice dice;
    private Coin coin;

    private DeckCards deckCards;

    public Duel() {
        initDuelFields();
    }

    public void start(String deck) {
        DeckCards deckCards = new DeckCards(deck);
        start(deckCards);
    }

    private void start(DeckCards deckCards) {
        boolean isSameDeck = this.deckCards == deckCards;
        this.deckCards = deckCards;
        DeckChecker checker = new DeckChecker(this.deckCards);
        if(checker.checkMainMin().checkMainMax().checkEx().checkSide().checkSingleCard().isError()) {
            Toast.makeText(Utils.getContext(), checker.getErrorInfo(), Toast.LENGTH_LONG).show();
            return;
        }
        if(!isSameDeck) {
            clearPreviousDuel(deckCards);
        }
        initDeck();
        initHandCards();
    }

    private void restart() {
        start(this.deckCards);
    }

    private void clearPreviousDuel(DeckCards deckCards) {
        for (Card card : deckCards.getAllCards()) {
            card.destroyBmp();
        }
    }

    private void initDuelFields() {
        lifePoint = new LifePoint();

        duelFields = new DuelFields();
        duelFields.getField(FieldType.DECK).setItem(new Deck(Utils.s(R.string.DECK), false));
        duelFields.getField(FieldType.EX_DECK).setItem(new Deck(Utils.s(R.string.EX), false));
        duelFields.getField(FieldType.GRAVEYARD).setItem(new Deck(Utils.s(R.string.GRAVEYARD), true));
        duelFields.getField(FieldType.BANISHED).setItem(new Deck(Utils.s(R.string.REMOVED), true));
        duelFields.getField(FieldType.TEMP).setItem(new Deck(Utils.s(R.string.TEMPORARY), true));

        handCards = new HandCards();

        infoBar = new InfoBar(handCards);

        dice = new Dice();
        coin = new Coin();
    }

    private void initDeck() {
        Deck deck = (Deck) duelFields.getField(FieldType.DECK).getItem();
        deck.getCardList().push(deckCards.getMainDeckCards());
        deck.getCardList().shuffle();
        Deck exDeck = (Deck) duelFields.getField(FieldType.EX_DECK).getItem();
        exDeck.getCardList().push(deckCards.getExDeckCards());
    }

    private void initHandCards() {
        Deck deck = (Deck) duelFields.getField(FieldType.DECK).getItem();
        handCards.add(deck.cardList.pop(5));
    }


    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new DuelRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new AbsoluteLayout(this);
        }
        return layout;
    }

    public LifePoint getLifePoint() {
        return lifePoint;
    }

    public LifePointCalculator getLifePointCalculator() {
        return lifePointCalculator;
    }

    public DuelFields getDuelFields() {
        return duelFields;
    }

    public HandCards getHandCards() {
        return handCards;
    }

    public CardSelector getCardSelector() {
        return cardSelector;
    }

    public InfoBar getInfoBar() {
        return infoBar;
    }

    public CardEffectWindow getCardEffectWindow() {
        return cardEffectWindow;
    }

    public Dice getDice() {
        return dice;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setLifePointCalculator(LifePointCalculator lifePointCalculator) {
        this.lifePointCalculator = lifePointCalculator;
    }

    public void setCardSelector(CardSelector cardSelector) {
        this.cardSelector = cardSelector;
    }

    public void setCardEffectWindow(CardEffectWindow cardEffectWindow) {
        this.cardEffectWindow = cardEffectWindow;
    }

    private Selectable currentSelectItem;

    public void unSelect() {
        if (currentSelectItem != null) {
            currentSelectItem.unSelect();
            currentSelectItem = null;
        }
        infoBar.clearInfo();
    }

    public void select(Selectable selectable) {
        if(selectable == null) {
            unSelect();
        } else {
            if(selectable != currentSelectItem) {
                currentSelectItem.unSelect();
            }
            currentSelectItem = selectable;
            currentSelectItem.select();
        }
        if(currentSelectItem instanceof Infoable) {
            infoBar.setInfo((Infoable) currentSelectItem);
        }
    }

    public Selectable getCurrentSelectItem() {
        return currentSelectItem;
    }

    private Drag drag;

    public Drag getDrag() {
        return drag;
    }

    public void setDrag(Drag drag) {
        this.drag = drag;
    }
}
