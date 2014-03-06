package org.msk86.ygoroid.newcore.deck;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.UserDefinedCard;
import org.msk86.ygoroid.newutils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeckChecker {
    public static final int MIN_MAIN_CARD_COUNT = 40;
    public static final int MAX_MAIN_CARD_COUNT = 60;
    public static final int MAX_EX_CARD_COUNT = 15;
    public static final int MAX_SIDE_CARD_COUNT = 15;
    public static final int MAX_SAME_CARD_COUNT = 3;

    DeckCards deckCards;
    boolean error;
    String errorInfo;

    Set<Integer> invalidCardIds;
    Set<String> invalidCardNames;

    public DeckChecker(DeckCards deckCards) {
        this.deckCards = deckCards;
        error = false;
        errorInfo = "";
        invalidCardIds = new HashSet<Integer>();
        invalidCardNames = new HashSet<String>();
    }

    public DeckChecker startCheck() {
        error = false;
        invalidCardIds.clear();
        invalidCardNames.clear();
        return this;
    }

    public DeckChecker checkMainMax() {
        if(error) return this;
        if(deckCards.getMainDeckCards().size() > MAX_MAIN_CARD_COUNT) {
            error = true;
            errorInfo = String.format(Utils.s(R.string.ERROR_MAIN), deckCards.getMainDeckCards().size());
        }
        return this;
    }

    public DeckChecker checkMainMin() {
        if(error) return this;
        if(deckCards.getMainDeckCards().size() < MIN_MAIN_CARD_COUNT) {
            errorInfo = String.format(Utils.s(R.string.ERROR_MAIN), deckCards.getMainDeckCards().size());
            error = true;
        }
        return this;
    }

    public DeckChecker checkEx() {
        if(error) return this;
        if(deckCards.getExDeckCards().size() > MAX_EX_CARD_COUNT) {
            errorInfo = String.format(Utils.s(R.string.ERROR_EX), deckCards.getExDeckCards().size());
            error = true;
        }
        return this;
    }

    public DeckChecker checkSide() {
        if(error) return this;
        if(deckCards.getSideDeckCards().size() > MAX_SIDE_CARD_COUNT) {
            errorInfo = String.format(Utils.s(R.string.ERROR_SIDE), deckCards.getSideDeckCards().size());
            error = true;
        }
        return this;
    }

    public DeckChecker checkSingleCard() {
        if(error) return this;
        List<Card> allCards = deckCards.getAllCards();

        for(Card card : allCards) {
            checkSingleCard(card, allCards);
        }

        if(error) {
            List<String> invalidCardNames = Utils.getDbHelper().loadNamesByIds(invalidCardIds);
            invalidCardNames.addAll(this.invalidCardNames);
            errorInfo = String.format(Utils.s(R.string.ERROR_CARD), invalidCardNames.toString());
        }

        return this;
    }

    private DeckChecker checkSingleCard(Card card, List<Card> allCards) {
        int count = 0;
        for(Card existCard: allCards) {
            if (!(existCard instanceof UserDefinedCard || existCard.getRealId().equals("0"))) {
                if (existCard.getRealId().equals(card.getRealId())) {
                    count++;
                }
            } else {
                if (existCard.getName().equals(card.getName())) {
                    count++;
                }
            }
        }
        if(count > MAX_SAME_CARD_COUNT) {
            error = true;
            if(card instanceof UserDefinedCard || card.getRealId().equals("0")) {
                invalidCardNames.add(card.getName());
            } else {
                invalidCardIds.add(Integer.parseInt(card.getRealId()));
            }
        }
        return this;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
