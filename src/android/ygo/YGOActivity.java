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
import android.webkit.WebViewClient;
import android.ygo.exception.CrashHandler;
import android.ygo.service.PersistencyService;
import android.ygo.upgrade.UpgradeHelper;
import android.ygo.upgrade.UpgradeMsgHandler;
import android.ygo.utils.Utils;
import android.ygo.views.DeckOnKeyProcessor;
import android.ygo.views.PlayMenuProcessor;
import android.ygo.views.PlayOnKeyProcessor;
import android.ygo.views.YGOView;
import android.ygo.views.deckbuilder.DeckBuilderView;
import android.ygo.views.dueldisk.DuelDiskView;

public class YGOActivity extends Activity {
    private PlayOnKeyProcessor duelKeyProcessor;
    private DeckOnKeyProcessor deckKeyProcessor;
    private PlayMenuProcessor menuProcessor;
    private View currentView;
    private DuelDiskView duelDiskView;
    private DeckBuilderView deckBuilderView;
    private WebView webView;

    private PersistencyService service;
    private ServiceConnection sConn;
    private Intent serviceIntent;

    private boolean mirror = false;

    private UpgradeMsgHandler upgradeMsgHandler;
    private UpgradeHelper upgradeHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        Utils.initInstance(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initWebView();

        showDuel();

        startService();

        upgradeMsgHandler = new UpgradeMsgHandler(this);
        upgradeHelper = new UpgradeHelper(this);
        upgradeHelper.startDetect();
    }

    private void initWebView() {
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void showTips() {
        webView.loadUrl(Utils.s(R.string.tips));
        currentView = webView;
        setContentView(webView);
    }

    public void showFeedback() {
        webView.loadUrl(Utils.s(R.string.feedback));
        currentView = webView;
        setContentView(webView);
    }

    public void showDuel() {
        super.setContentView(R.layout.duel_disk);
        duelDiskView = (DuelDiskView) findViewById(R.id.duelDiskView);
        if(!duelDiskView.isRunning()) {
            duelDiskView.resume();
        }
        currentView = duelDiskView;

        duelKeyProcessor = new PlayOnKeyProcessor(duelDiskView);
        menuProcessor = new PlayMenuProcessor(duelDiskView);

        duelDiskView.updateActionTime();
    }

    public void showDuelWithDeck(String deck) {
        showDuel();
        if(deck != null) {
            duelDiskView.getDuel().start(deck);
        }
        duelDiskView.updateActionTime();
    }

    public void showDeckBuilderWithDeck(String deck) {
        super.setContentView(R.layout.deck_builder);
        deckBuilderView = (DeckBuilderView) findViewById(R.id.deck_builder);
        if(!deckBuilderView.isRunning()) {
            deckBuilderView.resume();
        }
        currentView = deckBuilderView;
        deckKeyProcessor = new DeckOnKeyProcessor(deckBuilderView);
        if(deck != null) {
            deckBuilderView.loadDeck(deck);
        }
    }

    public boolean isMirror() {
        return mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(currentView == webView) {
            return super.onPrepareOptionsMenu(menu);
        } else if(currentView == duelDiskView) {
            return menuProcessor.onMenuPrepare(menu);
        } else {
            return super.onPrepareOptionsMenu(menu);
        }
    }

    @Override
    public boolean onMenuItemSelected(int id, MenuItem menuItem) {
        if(currentView == webView) {
            return super.onMenuItemSelected(id, menuItem);
        } else if(currentView == duelDiskView) {
            return menuProcessor.onMenuClick(menuItem);
        } else {
            return super.onMenuItemSelected(id, menuItem);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(currentView == webView) {
            if(keyCode == KeyEvent.KEYCODE_BACK) {
                showDuel();
                return true;
            }
        } else if(currentView == duelDiskView) {
            if(duelKeyProcessor != null) {
                return duelKeyProcessor.onKey(keyCode, event);
            }
        } else if(currentView == deckBuilderView) {
            if(deckKeyProcessor != null) {
                return deckKeyProcessor.onKey(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (currentView instanceof YGOView) {
            ((YGOView)currentView).pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentView instanceof YGOView) {
            YGOView ygoView = (YGOView) currentView;
            if(!ygoView.isRunning()) {
                ygoView.resume();
            }
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

    public UpgradeHelper getUpgradeHelper() {
        return upgradeHelper;
    }

    public UpgradeMsgHandler getUpgradeMsgHandler() {
        return upgradeMsgHandler;
    }

    private class PersistencyConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((PersistencyService.MyBinder) iBinder).getService();
            persistentDuel();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }

        private void persistentDuel() {
            if(service != null) {
                if(service.getDuel() != null) {
                    duelDiskView.setDuel(service.getDuel());
                } else {
                    service.setDuel(duelDiskView.getDuel());
                }
            }
        }
    }

}

