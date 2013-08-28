package org.msk86.ygoroid.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.ygo.R;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.FPSMaker;
import org.msk86.ygoroid.utils.Utils;

public abstract class YGOView extends SurfaceView implements Runnable {
    private static Bitmap BACKGROUND_BMP;

    static {
        BACKGROUND_BMP = Utils.readBitmapScaleByHeight(Configuration.texturePath() + "bg" + Configuration.cardImageSuffix(),
                Utils.screenHeight());
        if(BACKGROUND_BMP == null) {
            BACKGROUND_BMP = Utils.readBitmapScaleByHeight(R.raw.bg, Utils.screenHeight());
        }
    }

    private static final int ACTIVE_DRAW_DURATION = 1500;
    private Thread renderThread;
    private SurfaceHolder holder;
    private boolean running = false;
    private FPSMaker fpsMaker;
    private long actionTime;

    public YGOView(Context context, AttributeSet attrs) {
        super(context, attrs);
        renderThread = new Thread(this);
        holder = getHolder();
        this.setLongClickable(true);
        fpsMaker = new FPSMaker();
    }


    protected void drawVersion(Canvas canvas) {
        TextPaint paint = new TextPaint();
        paint.setColor(Configuration.fontColor());
        int fontSize = Utils.unitLength() / 7;
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        StaticLayout layout = new StaticLayout("V" + Utils.getVersion(), paint, Utils.unitLength() / 2, Layout.Alignment.ALIGN_OPPOSITE, 1, 0, false);
        Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
        helper.drawLayout(canvas, layout, Utils.unitLength() * 11 / 2, Utils.screenHeight() - fontSize - 2);
    }

    protected void drawFPS(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Configuration.fontColor());
        int fontSize = Utils.unitLength() / 7;
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        canvas.drawText("FPS:" + fpsMaker.getFPS(), Utils.unitLength() / 10, fontSize, paint);
    }

    protected void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
        if(BACKGROUND_BMP != null) {
            canvas.drawBitmap(BACKGROUND_BMP, helper.center(Utils.screenWidth(), BACKGROUND_BMP.getWidth()), 0, new Paint());
        }
    }

    abstract protected void doDraw(Canvas canvas);

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
                        doDraw(canvas);
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

    public boolean isRunning() {
        return running;
    }

    public void updateActionTime() {
        actionTime = System.currentTimeMillis();
    }
}
