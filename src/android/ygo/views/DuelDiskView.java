package android.ygo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.ygo.core.*;
import android.ygo.sqlite.CardsDBHelper;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DuelDiskView extends SurfaceView implements Runnable {
    private Thread renderThread;
    private SurfaceHolder holder;
    private boolean running = false;

    private PlayGestureDetector mGestureDetector;
    private SensorManager sensorManager;
    private CardsDBHelper dbHelper;

    private Paint painter;

    private Duel duel;

    public DuelDiskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        renderThread = new Thread(this);
        holder = getHolder();

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
        Card setMagicCard = dbHelper.loadById("35952884");
        Field f = duelFields.getMagicField(2);
        f.setItem(setMagicCard);

        // Monsters
        // set
        Card setCard = dbHelper.loadById("35952884");
        f = duelFields.getMonsterField(0);
        f.setItem(setCard);
        // atk monster
        Card card = dbHelper.loadById("35952884");
        f = duelFields.getMonsterField(1);
        f.setItem(card);
        // xyz + 2m
        Card material1 = dbHelper.loadById("35952884");
        Card material2 = dbHelper.loadById("35952884");
        Card xyzCard = dbHelper.loadById("84013237");
        Overlay overlay = new Overlay(material1);
        overlay.overlay(material2);
        overlay.overlay(xyzCard);
        f = duelFields.getMonsterField(2);
        f.setItem(overlay);
        // xyz + 1m
        Card material21 = dbHelper.loadById("35952884");
        Card material22 = dbHelper.loadById("35952884");
        Card xyzCard2 = dbHelper.loadById("84013237");
        Overlay overlay2 = new Overlay(material21);
        overlay2.overlay(material22);
        overlay2.overlay(xyzCard2);
        f = duelFields.getMonsterField(3);
        f.setItem(overlay2);
        // 2m
        Card material31 = dbHelper.loadById("35952884");
        Card material32 = dbHelper.loadById("35952884");
        Overlay overlay3 = new Overlay(material31);
        overlay3.overlay(material32);
        f = duelFields.getMonsterField(4);
        f.setItem(overlay3);


        List<String> ids = new ArrayList<String>();
        ids.add("32864");
        ids.add("11549357");
        ids.add("62121");
        ids.add("11548522");
        ids.add("359563");
        ids.add("12014404");
        ids.add("1412158");
        ids.add("11901678");
        ids.add("2203790");
        ids.add("9012916");
        ids.add("3627449");
        ids.add("10789972");
        ids.add("42685062");
        ids.add("69488544");
        ids.add("213326");
        ids.add("295517");
        ids.add("242146");
        ids.add("5318639");
        ids.add("403847");
        ids.add("1248895");
        ids.add("27551");
        ids.add("11593137");
        List<Card> cards = dbHelper.loadById(ids);
        Deck deck = (Deck) duelFields.getDeckField().getItem();
        deck.push(cards);

        CardList graveyard = (CardList) duelFields.getGraveyardField().getItem();
        Card usedCard = dbHelper.loadById("123");
        graveyard.push(usedCard);
        usedCard = dbHelper.loadByName("星尘龙");
        graveyard.push(usedCard);
        usedCard = dbHelper.loadByName("忘我画派");
        graveyard.push(usedCard);

        CardList removed = (CardList) duelFields.getRemovedField().getItem();
        Card removedCard = dbHelper.loadById("84013237");
        removed.push(removedCard);

        List<Card> hands = new ArrayList<Card>();
        hands.add(dbHelper.loadById("84013237"));
        hands.add(dbHelper.loadById("35952884"));
        hands.add(dbHelper.loadById("35952884"));
        hands.add(dbHelper.loadById("84013237"));
        hands.add(dbHelper.loadById("35952884"));
        hands.add(dbHelper.loadById("84013237"));
        hands.add(dbHelper.loadById("84013237"));
        duel.getHandCards().add(hands);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void drawDuelDisk(Canvas canvas) {
        drawBackground(canvas);
        Bitmap duelBmp = duel.toBitmap();
        if(Configuration.isMirror()) {
            canvas.translate(Utils.screenWidth() ,Utils.screenHeight());
            canvas.rotate(180);
        }
        canvas.drawBitmap(duelBmp, 0, 0, painter);
        duelBmp.recycle();
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        // IMAGE BACKGROUND
    }

    @Override
    public void run() {
        while (running) {
            if(!holder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = holder.lockCanvas();
            drawDuelDisk(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void resume(){
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void pause(){
        running = false;
        while(true){
            try{
                renderThread.join();
                break;
            }catch(InterruptedException e){
                // retry
            }
        }
    }
}
