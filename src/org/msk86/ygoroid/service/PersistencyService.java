package org.msk86.ygoroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.upgrade.Downloader;
import org.msk86.ygoroid.views.YGOView;

import java.util.HashMap;
import java.util.Map;

public class PersistencyService extends Service {
    private Map<YGOView, Item> cache = new HashMap<YGOView, Item>();

    private Downloader downloader;

    private MyBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void storeItem(YGOView view, Item item) {
        cache.put(view, item);
    }

    public Item fetchItem(YGOView view) {
        return cache.get(view);
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
