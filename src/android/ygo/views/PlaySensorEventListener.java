package android.ygo.views;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.ygo.core.HandCards;

public class PlaySensorEventListener implements SensorEventListener {

    DuelDiskView view;

    private int lastZ;

    public PlaySensorEventListener(DuelDiskView view) {
        this.view = view;
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        int z = (int)e.values[SensorManager.DATA_Z];
        if(lastZ != z) {
            HandCards handCards = view.getDuel().getHandCards();
            if(z >= 7) {
                handCards.setAll();
            } else {
                handCards.openAll();
            }
            view.invalidate();
        }
        lastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
