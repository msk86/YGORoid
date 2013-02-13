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
        Card setMagicCard = new Card("35952884", null, null);
        Field f = duelFields.getMagicField(2);
        f.setItem(setMagicCard);

        // Monsters
        // set
        Card setCard = new Card("35952884", null, null);
        f = duelFields.getMonsterField(0);
        f.setItem(setCard);
        // atk monster
        Card card = new Card("35952884", null, null);
        f = duelFields.getMonsterField(1);
        f.setItem(card);
        // xyz + 2m
        Card material1 = new Card("35952884", null, null);
        Card material2 = new Card("35952884", null, null);
        Card xyzCard = new Card("84013237", null, null);
        Overlay overlay = new Overlay(material1);
        overlay.overlay(material2);
        overlay.overlay(xyzCard);
        f = duelFields.getMonsterField(2);
        f.setItem(overlay);
        // xyz + 1m
        Card material21 = new Card("35952884", null, null);
        Card material22 = new Card("35952884", null, null);
        Card xyzCard2 = new Card("84013237", null, null);
        Overlay overlay2 = new Overlay(material21);
        overlay2.overlay(material22);
        overlay2.overlay(xyzCard2);
        f = duelFields.getMonsterField(3);
        f.setItem(overlay2);
        // 2m
        Card material31 = new Card("35952884", null, null);
        Card material32 = new Card("35952884", null, null);
        Overlay overlay3 = new Overlay(material31);
        overlay3.overlay(material32);
        f = duelFields.getMonsterField(4);
        f.setItem(overlay3);


        List<String> ids = new ArrayList<String>();
        // normal mon
        ids.add("32864");
        ids.add("11549357");
        // eff mon
        ids.add("62121");
        ids.add("11548522");
        // xyz mon
        ids.add("359563");
        ids.add("12014404");
        // fusion mon
        ids.add("1412158");
        ids.add("11901678");
        // sync mon
        ids.add("2203790");
        ids.add("9012916");
        // rat mon
        ids.add("3627449");
        ids.add("10789972");
        // tuner mon
        ids.add("42685062");
        // dual 69488544
        ids.add("69488544");
        //

        // normal magic
        ids.add("213326");
        // field magic
        ids.add("295517");
        // equip magic
        ids.add("242146");
        // speed magic
        ids.add("5318639");
        // forever magic
        ids.add("403847");

        // normal trap
        ids.add("1248895");
        // forever trap
        ids.add("27551");
        // counter trap
        ids.add("11593137");
        List<Card> cards = dbHelper.loadCard(ids);
        Deck deck = (Deck) duelFields.getDeckField().getItem();
        deck.push(cards);

        CardList graveyard = (CardList) duelFields.getGraveyardField().getItem();
        Card usedCard = new Card("35952884", null, null);
        graveyard.push(usedCard);

        CardList removed = (CardList) duelFields.getRemovedField().getItem();
        Card removedCard = new Card("84013237", null, null);
        removed.push(removedCard);

        List<Card> hands = new ArrayList<Card>();
        hands.add(new Card("84013237", null, null));
        hands.add(new Card("35952884", null, null));
        hands.add(new Card("35952884", null, null));
        hands.add(new Card("84013237", null, null));
        hands.add(new Card("35952884", null, null));
        hands.add(new Card("84013237", null, null));
        hands.add(new Card("84013237", null, null));
        duel.getHandCards().add(hands);
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
