package android.ygo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.*;
import android.webkit.WebView;
import android.ygo.exception.CrashHandler;
import android.ygo.service.PersistencyService;
import android.ygo.utils.Utils;
import android.ygo.views.DuelDiskView;
import android.ygo.views.PlayMenuProcessor;
import android.ygo.views.PlayOnKeyProcessor;

public class YGOActivity extends Activity {
    private PlayOnKeyProcessor keyProcessor;
    private PlayMenuProcessor menuProcessor;
    private View currentView;
    private DuelDiskView duelDiskView;
    private WebView tipView;

    private PersistencyService service;
    private ServiceConnection sConn;
    private Intent serviceIntent;

    private boolean mirror = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        Utils.initInstance(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        duelDiskView = new DuelDiskView(this, null);
        tipView = new WebView(this);
        tipView.loadUrl("http://www.baidu.com");

        setContentView(duelDiskView);

        keyProcessor = new PlayOnKeyProcessor(duelDiskView);
        menuProcessor = new PlayMenuProcessor(duelDiskView);

        startService();
    }

    @Override
    public void setContentView(View view) {
        currentView = view;
        super.setContentView(view);
    }

    public void showTips() {
        setContentView(tipView);
    }

    public void showDuel() {
        setContentView(duelDiskView);
    }

    public boolean isMirror() {
        return mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(currentView == tipView) {
            return super.onPrepareOptionsMenu(menu);
        } else {
            return menuProcessor.onMenuPrepare(menu);
        }
    }

    @Override
    public boolean onMenuItemSelected(int id, MenuItem menuItem) {
        if(currentView == tipView) {
            return super.onMenuItemSelected(id, menuItem);
        } else {
            return menuProcessor.onMenuClick(menuItem);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(currentView == tipView) {
            if(keyCode == KeyEvent.KEYCODE_BACK) {
                showDuel();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return keyProcessor.onKey(keyCode, event);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (duelDiskView != null) {
            duelDiskView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (duelDiskView != null) {
            duelDiskView.resume();
        }
    }

    public void startService() {
        serviceIntent = new Intent(this, PersistencyService.class);
        sConn = new PersistencyConnection();
        bindService(serviceIntent, sConn, Context.BIND_AUTO_CREATE);
    }

    public void stopService() {
        if (serviceIntent != null) {
            unbindService(sConn);
        }
    }

    public DuelDiskView getDuelDiskView() {
        return duelDiskView;
    }

    private class PersistencyConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((PersistencyService.MyBinder) iBinder).getService();
            if(service != null) {
                if(service.getDuel() != null) {
                    duelDiskView.setDuel(service.getDuel());
                } else {
                    service.setDuel(duelDiskView.getDuel());
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    }
}

