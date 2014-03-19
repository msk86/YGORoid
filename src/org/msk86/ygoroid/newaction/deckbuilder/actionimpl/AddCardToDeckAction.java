package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.widget.Toast;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.deck.DeckChecker;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.UserDefinedCard;
import org.msk86.ygoroid.newcore.impl.builder.DeckBuilder;
import org.msk86.ygoroid.newcore.impl.builder.ExDeckSection;
import org.msk86.ygoroid.newcore.impl.builder.MainDeckSection;
import org.msk86.ygoroid.newcore.impl.builder.SideDeckSection;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.CardNameView;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;

import java.util.List;

public class AddCardToDeckAction implements Action {
    DeckBuilderView deckBuilderView;
    CardNameView cardNameView;

    public AddCardToDeckAction(DeckBuilderView deckBuilderView, CardNameView cardNameView) {
        this.deckBuilderView = deckBuilderView;
        this.cardNameView = cardNameView;
    }

    @Override
    public void execute() {
        Card card = cardNameView.getCard().clone();
        DeckBuilder builder = deckBuilderView.getDeckBuilder();
        DeckCards cards = builder.getCards();
        List<Card> deck = null;
        if(card instanceof UserDefinedCard) {
            Container section = builder.getCurrentSection();
            if(section instanceof MainDeckSection) {
                deck = cards.getMainDeckCards();
            }
            if(section instanceof ExDeckSection) {
                deck = cards.getExDeckCards();
            }
            if(section instanceof SideDeckSection) {
                deck = cards.getSideDeckCards();
            }
        } else {
            if(builder.getCurrentSection() instanceof SideDeckSection) {
                deck = cards.getSideDeckCards();
            } else {
                if(card.isEx()) {
                    deck = cards.getExDeckCards();
                } else {
                    deck = cards.getMainDeckCards();
                }
            }
        }

        if(deck != null) {
            deck.add(card);
            DeckChecker checker = new DeckChecker(cards);
            checker.startCheck().checkMainMax().checkEx().checkSide().checkSingleCard();
            if(checker.isError()) {
                deck.remove(card);
                Toast.makeText(Utils.getContext(), checker.getErrorInfo(), Toast.LENGTH_LONG).show();
            }
        }

        builder.refreshDeck();
    }
}
