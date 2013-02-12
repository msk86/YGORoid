package android.ygo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.ygo.core.*;
import android.ygo.sqlite.CardsDBHelper;

import java.util.ArrayList;
import java.util.List;

public class DuelDiskView extends View {
    private PlayGestureDetector mGestureDetector;
    private SensorManager sensorManager;
    private CardsDBHelper dbHelper;

    private Paint painter;

    private Duel duel;

    public DuelDiskView(Context context) {
        super(context);
        painter = new Paint();
        duel = new Duel();
        mGestureDetector = new PlayGestureDetector(new PlayGestureListener(this));
        sensorManager = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new PlaySensorEventListener(this), sensor, SensorManager.SENSOR_DELAY_GAME);
        this.setLongClickable(true);
        dbHelper = new CardsDBHelper(context, 1);

        initDuelDiskTest();

    }

    public Duel getDuel() {
        return duel;
    }

    private void initDuelDiskTest() {
        DuelFields duelFields = duel.getDuelFields();

        // set magic
        Card setMagicCard = new Card("12345678", null, CardType.MAGIC, true, true);
        Field f = duelFields.getMagicField(2);
        f.setItem(setMagicCard);

        // Monsters
        // set
        Card setCard = new Card("12345678", null, CardType.SYNC_MONSTER, false, true);
        f = duelFields.getMonsterField(0);
        f.setItem(setCard);
        // atk monster
        Card card = new Card("12345678", null, CardType.SYNC_MONSTER, true, false);
        f = duelFields.getMonsterField(1);
        f.setItem(card);
        // xyz + 2m
        Card material1 = new Card("12345678", null, CardType.SYNC_MONSTER, false, true);
        Card material2 = new Card("12345678", null, CardType.SYNC_MONSTER, false, true);
        Card xyzCard = new Card("23456789", null, CardType.XYZ_MONSTER, true, false);
        Overlay overlay = new Overlay(material1);
        overlay.overlay(material2);
        overlay.overlay(xyzCard);
        f = duelFields.getMonsterField(2);
        f.setItem(overlay);
        // xyz + 1m
        Card material21 = new Card("12345678", null, CardType.SYNC_MONSTER, false, true);
        Card material22 = new Card("12345678", null, CardType.SYNC_MONSTER, false, true);
        Card xyzCard2 = new Card("23456789", null, CardType.XYZ_MONSTER, false, false);
        Overlay overlay2 = new Overlay(material21);
        overlay2.overlay(material22);
        overlay2.overlay(xyzCard2);
        f = duelFields.getMonsterField(3);
        f.setItem(overlay2);
        // 2m
        Card material31 = new Card("12345678", null, CardType.SYNC_MONSTER, false, true);
        Card material32 = new Card("12345678", null, CardType.SYNC_MONSTER, false, false);
        Overlay overlay3 = new Overlay(material31);
        overlay3.overlay(material32);
        f = duelFields.getMonsterField(4);
        f.setItem(overlay3);


        List<String> ids = new ArrayList<String>();
        ids.add("39272762");
        ids.add("39272762");
        ids.add("35952884");
        ids.add("35952884");
        ids.add("84013237");
        ids.add("46986414");
        List<Card> cards = dbHelper.loadCard(ids);
//        List<Card> cards = new ArrayList<Card>();
//        cards.add(new Card("12345678", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("12345678", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("12345678", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("12345678", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
//        cards.add(new Card("23456789", null));
        Deck deck = (Deck) duelFields.getDeckField().getItem();
        deck.push(cards);

        CardList graveyard = (CardList) duelFields.getGraveyardField().getItem();
        Card usedCard = new Card("12345678", null, CardType.SYNC_MONSTER, false, true);
        graveyard.push(usedCard);

        CardList removed = (CardList) duelFields.getRemovedField().getItem();
        Card removedCard = new Card("23456789", null, CardType.XYZ_MONSTER, false, true);
        removed.push(removedCard);

        List<Card> hands = new ArrayList<Card>();
        hands.add(new Card("12345678", null, CardType.SYNC_MONSTER, false, true));
        hands.add(new Card("12345678", null, CardType.SYNC_MONSTER, false, true));
        hands.add(new Card("12345678", null, CardType.SYNC_MONSTER, false, true));
        hands.add(new Card("12345678", null, CardType.SYNC_MONSTER, false, true));
        hands.add(new Card("12345678", null, CardType.SYNC_MONSTER, false, true));
        hands.add(new Card("12345678", null, CardType.SYNC_MONSTER, false, true));
        hands.add(new Card("12345678", null, CardType.SYNC_MONSTER, false, true));
        duel.getHandCards().add(hands);

        duel.getInfoWindow().setInfo("No 39 希望皇 霍普 R4 2500/2000 光 战士");

        CardSelector selector = new CardSelector(deck, deck);
        duel.setCardSelector(selector);
    }

    @Override
    public void draw(Canvas canvas) {
        drawBackground(canvas);
        long st = System.currentTimeMillis();
        canvas.drawBitmap(duel.toBitmap(), 0, 0, painter);
        long et = System.currentTimeMillis();
        Log.e("YGO", "DRAW_TIME:" + (et - st));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        // IMAGE BACKGROUND
    }
}
