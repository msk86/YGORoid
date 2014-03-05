package org.msk86.ygoroid.views.newdueldisk;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import org.msk86.ygoroid.newcore.impl.HandCards;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.newutils.Utils;

public class PlaySensorEventListener implements SensorEventListener {

    DuelDiskView view;

    public PlaySensorEventListener(DuelDiskView view) {
        this.view = view;
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        if (!Configuration.configProperties(Configuration.PROPERTY_GRAVITY_ENABLE)) {
            return;
        }
        HandCards handCards = view.getDuel().getHandCards();
        float zLimit = 8f;
        float z = e.values[SensorManager.DATA_Z];
        float x = e.values[SensorManager.DATA_X];

        boolean changed = false;
        if (z >= zLimit) {
            if (z >= zLimit + 0.3) {
                if (handCards.getCardList().isOpen()) {
                    handCards.getCardList().setAll();
                    changed = true;
                }
            }
        } else {
            if (z <= zLimit - 0.3) {
                if (Utils.getContext().isMirror() != x >= 0) {
                    if (!handCards.getCardList().isOpen()) {
                        handCards.getCardList().openAll();
                        changed = true;
                    }
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
