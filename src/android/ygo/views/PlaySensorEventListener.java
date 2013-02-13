package android.ygo.views;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.ygo.core.HandCards;

public class PlaySensorEventListener implements SensorEventListener {

    DuelDiskView view;

    public PlaySensorEventListener(DuelDiskView view) {
        this.view = view;
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        float z = e.values[SensorManager.DATA_Z];
        boolean changed = false;
        HandCards handCards = view.getDuel().getHandCards();
        if (z >= 7) {
            if(z >= 7.3) {
                if(!handCards.isSet()) {
                    handCards.setAll();
                    changed = true;
                }
            }
        } else {
            if(z <= 6.7) {
                if(handCards.isSet()) {
                    handCards.openAll();
                    changed = true;
                }
            }
        }
        if(changed) {
            view.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
