package org.msk86.ygoroid.views.newdueldisk;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import org.msk86.ygoroid.newcore.impl.CardList;
import org.msk86.ygoroid.newcore.impl.HandCards;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.newutils.Utils;

public class PlaySensorEventListener implements SensorEventListener {
    DuelDiskView view;
    float zLimit =  8f;
    float previousZ = 8f;

    public PlaySensorEventListener(DuelDiskView view) {
        this.view = view;
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        Log.e("YGO", "sensor change");
//        if (!Configuration.configProperties(Configuration.PROPERTY_GRAVITY_ENABLE)) {
//            return;
//        }
        HandCards handCards = view.getDuel().getHandCards();
        float z = e.values[SensorManager.DATA_Z];
        float x = e.values[SensorManager.DATA_X];

        boolean changed = false;
        CardList cardList = handCards.getCardList();
        Log.e("YGO", "Z:" + z + ", PZ:" + previousZ);
        if (z >= zLimit + 0.3 && previousZ < zLimit + 0.3) {
            cardList.setAll();
            cardList.setOpen(false);
            changed = true;
        }
        if (z <= zLimit - 0.3 && previousZ > zLimit - 0.3) {
            if (Utils.getContext().isMirror() != x >= 0) {
                cardList.openAll();
                cardList.setOpen(true);
                changed = true;
            }
        }
        if (changed) {
            view.updateActionTime();
        }
        previousZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
