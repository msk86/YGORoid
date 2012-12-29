package android.ygo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.ygo.core.*;

import java.util.ArrayList;
import java.util.List;

public class DuelDiskView extends View {

    private Paint painter;

    private DuelFields duelFields;

    public DuelDiskView(Context context) {
        super(context);
        initDuelDisk();
    }

    private void initDuelDisk() {
        painter = new Paint();
        duelFields = new DuelFields();
        Card card = new Card("12345678", CardType.SYNC_MONSTER, true, false);
        Field f = duelFields.getMonsterField(2);
        f.setItem(card);
        Card setCard = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        f = duelFields.getMonsterField(1);
        f.setItem(setCard);
        Card setMagicCard = new Card("12345678", CardType.MAGIC, true, true);
        f = duelFields.getMagicField(2);
        f.setItem(setMagicCard);

        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card("12345678"));
        Deck deck = new Deck(cards);
        f = duelFields.getDeckField();
        f.setItem(deck);

        CardList graveyard = new CardList();
        Card usedCard = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        graveyard.push(usedCard);
        f = duelFields.getGraveyardField();
        f.setItem(graveyard);

        CardList removed = new CardList();
        Card removedCard = new Card("23456789", CardType.XYZ_MONSTER, false, true);
        removed.push(removedCard);
        f = duelFields.getRemovedField();
        f.setItem(removed);

        duelFields.select(f);
    }

    @Override
    public void draw(Canvas canvas){
        drawBackground(canvas);
        canvas.drawBitmap(duelFields.toBitmap(), 0, 0, painter);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        // IMAGE BACKGROUND
    }

}
