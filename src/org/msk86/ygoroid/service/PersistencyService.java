package org.msk86.ygoroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import org.msk86.ygoroid.core.Duel;

public class PersistencyService extends Service {

    private Duel duel;

    private MyBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public Duel getDuel() {
        return duel;
    }

    public void setDuel(Duel duel) {
        this.duel = duel;
    }

    public class MyBinder extends Binder {
        public PersistencyService getService() {
            return PersistencyService.this;
        }
    }
}
