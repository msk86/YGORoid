package android.ygo.views.dueldisk;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.ygo.core.Duel;
import android.ygo.core.HandCards;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class PlaySensorEventListener implements SensorEventListener {

    DuelDiskView view;

    public PlaySensorEventListener(DuelDiskView view) {
        this.view = view;
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        if(!Configuration.configProperties(Configuration.PROPERTY_GRAVITY_ENABLE)) {
            return;
        }
        HandCards handCards = view.getDuel().getHandCards();
        Duel duel = view.getDuel();
        if(duel.getSideWindow() == null) {
            if(view.getDuel().getCardWindow() != null
                    || view.getDuel().getCardSelector() != null) {
                handCards.setAll();
                view.updateActionTime();
                return;
            }
        } else {
            handCards.openAll();
            view.updateActionTime();
            return;
        }

        float zLimit = 8f;
        float z = e.values[SensorManager.DATA_Z];
        float x = e.values[SensorManager.DATA_X];

        boolean changed = false;
        if (z >= zLimit) {
            if (z >= zLimit + 0.3) {
                if (!handCards.isSet()) {
                    handCards.setAll();
                    changed = true;
                }
            }
        } else {
            if (z <= zLimit - 0.3) {
                if(Utils.getContext().isMirror() != x >= 0) {
                    if (handCards.isSet()) {
                        handCards.openAll();
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
