package org.msk86.ygoroid.views.newdueldisk;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newcore.impl.side.SideChanger;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.views.OnKeyProcessor;
import org.msk86.ygoroid.views.OnMenuProcessor;
import org.msk86.ygoroid.views.YGOView;

public class DuelDiskView extends YGOView {

    private PlayGestureDetector mGestureDetector;
    private SensorManager sensorManager;

    private Duel duel;

    public DuelDiskView(Context context, AttributeSet attrs) {
        super(context, attrs);

        duel = new Duel();
        mGestureDetector = new PlayGestureDetector(new PlayGestureListener(this));
        sensorManager = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new PlaySensorEventListener(this), sensor, SensorManager.SENSOR_DELAY_GAME);

        initAbout();
    }

    public Duel getDuel() {
        return duel;
    }

    public void setDuel(Duel duel) {
        this.duel = duel;
    }

    private void initAbout() {
//        duel.start("交通机甲.ydk");
//        sideChanger = new SideChanger(duel.getDeckCards());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    @Override
    public void doDraw(Canvas canvas) {
        drawBackground(canvas);
        duel.getRenderer().draw(canvas, 0, 0);
//        sideChanger.getRenderer().draw(canvas, 0, 0);
//        drawVersion(canvas);
        if (Configuration.configProperties(Configuration.PROPERTY_FPS_ENABLE)) {
//            drawFPS(canvas);
        }
    }

    OnKeyProcessor onKeyProcessor;
    @Override
    public OnKeyProcessor getOnKeyProcessor() {
        if(onKeyProcessor == null) {
            onKeyProcessor = new PlayOnKeyProcessor(this);
        }
        return onKeyProcessor;
    }

    OnMenuProcessor onMenuProcessor;
    @Override
    public OnMenuProcessor getOnMenuProcessor() {
        if(onMenuProcessor == null) {
            onMenuProcessor = new PlayOnMenuProcessor(this);
        }
        return onMenuProcessor;
    }

    @Override
    public String getDuelState() {
        return YGOView.DUEL_STATE_DUEL;
    }
}
