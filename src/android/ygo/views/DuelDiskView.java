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
import android.ygo.core.Duel;
import android.ygo.core.DuelFields;
import android.ygo.core.Field;
import android.ygo.core.SpCard;
import android.ygo.utils.Configuration;
import android.ygo.utils.FPSMaker;
import android.ygo.utils.Utils;

public class DuelDiskView extends SurfaceView implements Runnable {
    private static Bitmap BACKGROUND_BMP;

    static {
        try {
            BACKGROUND_BMP = Utils.readBitmapScaleByHeight(Configuration.texturePath() + "bg" + Configuration.cardImageSuffix(),
                    Utils.screenHeight());
        } catch (Exception e) {
            BACKGROUND_BMP = null;
        }
    }

    private static final int ACTIVE_DRAW_DURATION = 1500;
    private Thread renderThread;
    private SurfaceHolder holder;
    private boolean running = false;
    private FPSMaker fpsMaker;
    private long actionTime;

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

    public void setDuel(Duel duel) {
        this.duel = duel;
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

    public void drawDuelDisk(Canvas canvas) {
        drawBackground(canvas);
        duel.draw(canvas, 0, 0);
        drawVersion(canvas);
        if (Configuration.showFPS()) {
            drawFPS(canvas);
        }
    }

    private void drawVersion(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Configuration.fontColor());
        int fontSize = Utils.unitLength() / 7;
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        canvas.drawText("V" + Utils.getVersion(), Utils.unitLength() * 11 / 10, fontSize, paint);
    }

    private void drawFPS(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Configuration.fontColor());
        int fontSize = Utils.unitLength() / 7;
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        canvas.drawText("FPS:" + fpsMaker.getFPS(), Utils.unitLength() * 9 / 5, fontSize, paint);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if(BACKGROUND_BMP != null) {
            canvas.drawBitmap(BACKGROUND_BMP, 0, 0, new Paint());
        }
    }

    @Override
    public void run() {
        fpsMaker.setTime(System.nanoTime());
        while (running) {
            fpsMaker.setLimitTime(System.currentTimeMillis());
            if (actionTime + ACTIVE_DRAW_DURATION >= System.currentTimeMillis()) {
                if (!holder.getSurface().isValid()) {
                    continue;
                }
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas();
                    synchronized (holder) {
                        drawDuelDisk(canvas);
                    }
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }

            }

            fpsMaker.limitFPS();
            fpsMaker.makeFPS();
        }
    }

    public void resume() {
        running = true;
        updateActionTime();
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void pause() {
        running = false;
    }

    public void updateActionTime() {
        actionTime = System.currentTimeMillis();
    }
}
