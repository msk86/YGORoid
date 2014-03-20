package org.msk86.ygoroid.views.newdueldisk;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newcore.impl.SpCard;
import org.msk86.ygoroid.views.OnKeyProcessor;
import org.msk86.ygoroid.views.OnMenuProcessor;
import org.msk86.ygoroid.views.YGOView;

import java.util.List;

public class DuelDiskView extends YGOView {

    private PlayGestureDetector mGestureDetector;
    private SensorManager sensorManager;

    private Duel duel;

    public DuelDiskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        duel = new Duel();
        mGestureDetector = new PlayGestureDetector(context, new PlayGestureListener(this));
        sensorManager = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new PlaySensorEventListener(this), sensor, SensorManager.SENSOR_DELAY_GAME);

        initAbout();
    }

    public Duel getDuel() {
        return duel;
    }

    private void initAbout() {
        List<Field> monsterZones = duel.getDuelFields().getFields(FieldType.MONSTER);
        List<Field> magicZones = duel.getDuelFields().getFields(FieldType.MAGIC_TRAP);
        monsterZones.get(2).setItem(SpCard.createMsk86());
        monsterZones.get(1).setItem(SpCard.createHeaven());
        monsterZones.get(3).setItem(SpCard.createZh99998());
        SpCard quickStart = SpCard.createQuickStart();
        magicZones.get(2).setItem(quickStart);
        duel.select(quickStart);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    @Override
    public void doDraw(Canvas canvas) {
        drawBackground(canvas);
        duel.getRenderer().draw(canvas, 0, 0);
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

    @Override
    public void deallocateMemory() {
        duel.recycleUselessBmp();
    }
}
