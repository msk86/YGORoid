package android.ygo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.ygo.core.Duel;
import android.ygo.core.DuelFields;
import android.ygo.core.Field;
import android.ygo.core.SpCard;
import android.ygo.utils.Configuration;
import android.ygo.utils.FPSMaker;
import android.ygo.utils.Utils;

public class DuelDiskView extends SurfaceView implements Runnable {
    private Thread renderThread;
    private SurfaceHolder holder;
    private boolean running = false;
    private FPSMaker fpsMaker;

    private PlayGestureDetector mGestureDetector;
    private SensorManager sensorManager;

    private Duel duel;

    public DuelDiskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        renderThread = new Thread(this);
        holder = getHolder();

        duel = new Duel();
        mGestureDetector = new PlayGestureDetector(new PlayGestureListener(this));
        sensorManager = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new PlaySensorEventListener(this), sensor, SensorManager.SENSOR_DELAY_GAME);
        this.setLongClickable(true);

        fpsMaker = new FPSMaker();

        initAbout();
    }

    public Duel getDuel() {
        return duel;
    }

    private void initAbout() {
        DuelFields duelFields = duel.getDuelFields();
        Field f = duelFields.getMonsterField(2);
        SpCard msk86 = SpCard.createDeveloper();
        f.setItem(msk86);
        duel.select(msk86, f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void drawDuelDisk(Canvas canvas) {
        drawBackground(canvas);
        duel.draw(canvas, 0, 0);
        if(Configuration.showFPS()) {
            Paint paint = new Paint();
            paint.setColor(Configuration.fontColor());
            paint.setTextSize(20);
            canvas.drawText("FPS: "+fpsMaker.getFPS(), Utils.unitLength(), 20, paint);
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        // IMAGE BACKGROUND
    }

    @Override
    public void run() {
        fpsMaker.setTime(System.nanoTime());
        while (running) {
            fpsMaker.setLimitTime(System.currentTimeMillis());

            if (!holder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = holder.lockCanvas();
            drawDuelDisk(canvas);
            holder.unlockCanvasAndPost(canvas);

            fpsMaker.limitFPS();
            fpsMaker.makeFPS();
        }
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }
}
