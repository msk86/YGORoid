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
        if(view.getDuel().getSideWindow() != null) {
            return;
        }
        float zLimit = 8f;
        float z = e.values[SensorManager.DATA_Z];
        boolean changed = false;
        HandCards handCards = view.getDuel().getHandCards();
        if (z >= zLimit) {
            if (z >= zLimit + 0.3) {
                if (!handCards.isSet()) {
                    handCards.setAll();
                    changed = true;
                }
            }
        } else {
            if (z <= zLimit - 0.3) {
                if (handCards.isSet()) {
                    handCards.openAll();
                    changed = true;
                }
            }
        }
        if (changed) {
            view.updateActionTime();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
