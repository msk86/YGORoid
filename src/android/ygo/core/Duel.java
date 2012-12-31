package android.ygo.core;

import android.graphics.Bitmap;

public class Duel implements Item {
    private DuelFields duelFields;

    public Duel() {
        duelFields = new DuelFields();
        Deck deck = new Deck("DECK");
        Deck exDeck = new Deck("EX");
        CardList graveyard = new CardList("GRAVEYARD");
        CardList removed = new CardList("REMOVED");
        CardList temp = new CardList("TEMPORARY");
        duelFields.getDeckField().setItem(deck);
        duelFields.getExDeckField().setItem(exDeck);
        duelFields.getGraveyardField().setItem(graveyard);
        duelFields.getRemovedField().setItem(removed);
        duelFields.getTempField().setItem(temp);
    }


    public DuelFields getDuelFields() {
        return duelFields;
    }

    @Override
    public Bitmap toBitmap() {
        return duelFields.toBitmap();
    }
}
