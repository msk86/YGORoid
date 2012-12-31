package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Duel implements Item {
    private DuelFields duelFields;

    private HandCards handCards;

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

        handCards = new HandCards();
    }


    public DuelFields getDuelFields() {
        return duelFields;
    }

    public HandCards getHandCards() {
        return handCards;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap duelBmp = Bitmap.createBitmap(Utils.unitLength() * 6, Utils.unitLength() * 4, Bitmap.Config.ARGB_8888);

        Bitmap fieldBmp = duelFields.toBitmap();
        Bitmap handBmp = handCards.toBitmap();

        Canvas canvas = new Canvas(duelBmp);
        Paint paint = new Paint();
        Utils.drawBitmapOnCanvas(canvas, fieldBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);

        int padding = Utils.cardHeight() / 15;

        Utils.drawBitmapOnCanvas(canvas, handBmp, paint, Utils.DRAW_POSITION_FIRST, fieldBmp.getHeight() + padding);

        return duelBmp;
    }
}
