package org.msk86.ygoroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import org.msk86.ygoroid.upgrade.Downloader;

public class PersistencyService extends Service {
    private Downloader downloader;

    private MyBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    public class MyBinder extends Binder {
        public PersistencyService getService() {
            return PersistencyService.this;
        }
    }
}
