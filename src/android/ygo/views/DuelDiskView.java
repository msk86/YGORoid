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
        painter = new Paint();
        initDuelDisk();

        initDuelDiskTest();
    }

    private void initDuelDisk() {
        duelFields = new DuelFields();
        Deck deck = new Deck();
        Deck exDeck = new Deck();
        CardList graveyard = new CardList();
        CardList removed = new CardList();
        CardList temp = new CardList();
        duelFields.getDeckField().setItem(deck);
        duelFields.getExDeckField().setItem(exDeck);
        duelFields.getGraveyardField().setItem(graveyard);
        duelFields.getRemovedField().setItem(removed);
        duelFields.getTempField().setItem(temp);
    }

    private void initDuelDiskTest() {
        // set magic
        Card setMagicCard = new Card("12345678", CardType.MAGIC, true, true);
        Field f = duelFields.getMagicField(2);
        f.setItem(setMagicCard);

        // Monsters
        // set
        Card setCard = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        f = duelFields.getMonsterField(0);
        f.setItem(setCard);
        // atk monster
        Card card = new Card("12345678", CardType.SYNC_MONSTER, true, false);
        f = duelFields.getMonsterField(1);
        f.setItem(card);
        // xyz + 2m
        Card material1 = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        Card material2 = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        Card xyzCard = new Card("23456789", CardType.XYZ_MONSTER, true, false);
        Overlay overlay = new Overlay(material1);
        overlay.overlay(material2);
        overlay.overlay(xyzCard);
        f = duelFields.getMonsterField(2);
        f.setItem(overlay);
//        overlay.select();
        // xyz + 1m
        Card material21 = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        Card material22 = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        Card xyzCard2 = new Card("23456789", CardType.XYZ_MONSTER, false, false);
        Overlay overlay2 = new Overlay(material21);
        overlay2.overlay(material22);
        overlay2.overlay(xyzCard2);
        overlay2.select();
        f = duelFields.getMonsterField(3);
        f.setItem(overlay2);
        // 2m
        Card material31 = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        Card material32 = new Card("12345678", CardType.SYNC_MONSTER, false, false);
        Overlay overlay3 = new Overlay(material31);
        overlay3.overlay(material32);
        overlay3.select();
        f = duelFields.getMonsterField(4);
        f.setItem(overlay3);

        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card("12345678"));
        Deck deck = (Deck)duelFields.getDeckField().getItem();
        deck.push(cards);

        CardList graveyard = (CardList)duelFields.getGraveyardField().getItem();
        Card usedCard = new Card("12345678", CardType.SYNC_MONSTER, false, true);
        graveyard.push(usedCard);

        CardList removed = (CardList)duelFields.getRemovedField().getItem();
        Card removedCard = new Card("23456789", CardType.XYZ_MONSTER, false, true);
        removed.push(removedCard);
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
