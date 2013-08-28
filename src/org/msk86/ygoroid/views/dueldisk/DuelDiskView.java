package org.msk86.ygoroid.views.dueldisk;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import org.msk86.ygoroid.core.Duel;
import org.msk86.ygoroid.core.DuelFields;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.core.SpCard;
import org.msk86.ygoroid.utils.Configuration;
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
        DuelFields duelFields = duel.getDuelFields();
        Field f1 = duelFields.getMonsterField(2);
        SpCard msk86 = SpCard.createMsk86();
        f1.setItem(msk86);
        Field f2 = duelFields.getMagicField(2);
        SpCard heaven = SpCard.createHeaven();
        f2.setItem(heaven);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void doDraw(Canvas canvas) {
        drawBackground(canvas);
        duel.draw(canvas, 0, 0);
        drawVersion(canvas);
        if (Configuration.configProperties(Configuration.PROPERTY_FPS_ENABLE)) {
            drawFPS(canvas);
        }
    }
}
