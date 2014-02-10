package org.msk86.ygoroid;

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
import org.msk86.ygoroid.exception.CrashHandler;
import org.msk86.ygoroid.service.PersistencyService;
import org.msk86.ygoroid.upgrade.Downloader;
import org.msk86.ygoroid.upgrade.UpgradeHelper;
import org.msk86.ygoroid.upgrade.UpgradeMsgHandler;
import org.msk86.ygoroid.utils.Utils;
import org.msk86.ygoroid.views.DeckOnKeyProcessor;
import org.msk86.ygoroid.views.PlayMenuProcessor;
import org.msk86.ygoroid.views.PlayOnKeyProcessor;
import org.msk86.ygoroid.views.YGOView;
import org.msk86.ygoroid.views.deckbuilder.DeckBuilderView;
import org.msk86.ygoroid.views.dueldisk.DuelDiskView;
import org.msk86.ygoroid.views.logo.LogoView;

public class YGOActivity extends Activity {
    private PlayOnKeyProcessor duelKeyProcessor;
    private DeckOnKeyProcessor deckKeyProcessor;
    private PlayMenuProcessor menuProcessor;
    private View currentView;
    private DuelDiskView duelDiskView;
    private DeckBuilderView deckBuilderView;
    private WebView webView;
    private LogoView logoView;

    private PersistencyService service;
    private ServiceConnection sConn;
    private Intent serviceIntent;

    private boolean mirror = false;

    private Downloader downloader;
    private UpgradeMsgHandler upgradeMsgHandler;
    private UpgradeHelper upgradeHelper;

    private static final String DUEL_STATE = "DUEL_STATE";
    private static final String DUEL_STATE_DUEL = "DUEL_STATE_DUEL";
    private static final String DUEL_STATE_DECK = "DUEL_STATE_DECK";
    private boolean newestDatabase = false;
    private boolean wifiChecked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        Utils.initInstance(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initWebView();

        if (savedInstanceState != null) {
            if (DUEL_STATE_DECK.equals(savedInstanceState.getString(DUEL_STATE))) {
                showDeckBuilderWithDeck(Utils.findTempDeck());
            } else {
                showDuel();
            }
        } else {
            showDuel();
            showLogo();
        }

        startService();

        downloader = new Downloader(this);
        upgradeMsgHandler = new UpgradeMsgHandler(this);
        upgradeHelper = new UpgradeHelper(this);
        upgradeHelper.startCheck();
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

    public void showHint() {
        webView.loadUrl(Utils.s(R.string.hint));
        currentView = webView;
        setContentView(webView);
    }

    public void showFeedback() {
        webView.loadUrl(Utils.s(R.string.feedback));
        currentView = webView;
        setContentView(webView);
    }

    public void showLogo() {
        logoView = new LogoView(this);
        super.setContentView(logoView);
        currentView = logoView;
    }

    public void showDuel() {
        super.setContentView(R.layout.duel_disk);
        duelDiskView = (DuelDiskView) findViewById(R.id.duelDiskView);
        if (!duelDiskView.isRunning()) {
            duelDiskView.resume();
        }
        currentView = duelDiskView;

        duelKeyProcessor = new PlayOnKeyProcessor(duelDiskView);
        menuProcessor = new PlayMenuProcessor(duelDiskView);

        duelDiskView.updateActionTime();
    }

    public void showDuelWithDeck(String deck) {
        showDuel();
        if (deck != null) {
            duelDiskView.getDuel().start(deck);
        }
        duelDiskView.updateActionTime();
    }

    public void showDeckBuilderWithDeck(String deck) {
        super.setContentView(R.layout.deck_builder);
        deckBuilderView = (DeckBuilderView) findViewById(R.id.deck_builder);
        if (!deckBuilderView.isRunning()) {
            deckBuilderView.resume();
        }
        currentView = deckBuilderView;
        deckKeyProcessor = new DeckOnKeyProcessor(deckBuilderView);
        if (deck != null) {
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
        if (currentView == webView) {
            return super.onPrepareOptionsMenu(menu);
        } else if (currentView == duelDiskView) {
            return menuProcessor.onMenuPrepare(menu);
        } else {
            return super.onPrepareOptionsMenu(menu);
        }
    }

    @Override
    public boolean onMenuItemSelected(int id, MenuItem menuItem) {
        if (currentView == webView) {
            return super.onMenuItemSelected(id, menuItem);
        } else if (currentView == duelDiskView) {
            return menuProcessor.onMenuClick(menuItem);
        } else {
            return super.onMenuItemSelected(id, menuItem);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (currentView == webView) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                showDuel();
                return true;
            }
        } else if (currentView == duelDiskView) {
            if (duelKeyProcessor != null) {
                return duelKeyProcessor.onKey(keyCode, event);
            }
        } else if (currentView == deckBuilderView) {
            if (deckKeyProcessor != null) {
                return deckKeyProcessor.onKey(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (currentView instanceof YGOView) {
            ((YGOView) currentView).pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentView instanceof YGOView) {
            YGOView ygoView = (YGOView) currentView;
            if (!ygoView.isRunning()) {
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

    public LogoView getLogoView() {
        return logoView;
    }

    public UpgradeHelper getUpgradeHelper() {
        return upgradeHelper;
    }

    public UpgradeMsgHandler getUpgradeMsgHandler() {
        return upgradeMsgHandler;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public void showInfo(String info) {
        if (currentView == deckBuilderView) {
            deckBuilderView.getInfoWindow().setInfo(info);
            deckBuilderView.updateActionTime();
        }
        if (currentView == duelDiskView) {
            duelDiskView.getDuel().getWindow().setInfo(info);
            duelDiskView.updateActionTime();
        }
    }

    public void setNewestDatabase() {
        newestDatabase = true;
    }

    public boolean isNewestDatabase() {
        return newestDatabase;
    }

    public boolean isWifiChecked() {
        return wifiChecked;
    }

    public void setWifiChecked() {
        this.wifiChecked = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentView == deckBuilderView) {
            outState.putString(DUEL_STATE, DUEL_STATE_DECK);
        } else {
            outState.putString(DUEL_STATE, DUEL_STATE_DUEL);
        }

        super.onSaveInstanceState(outState);
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
            if (service != null && duelDiskView != null) {
                if (service.getDuel() != null) {
                    duelDiskView.setDuel(service.getDuel());
                } else {
                    service.setDuel(duelDiskView.getDuel());
                }
            }
            if (service != null && downloader != null) {
                service.setDownloader(downloader);
            }
        }
    }

}

