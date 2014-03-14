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
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.service.PersistencyService;
import org.msk86.ygoroid.upgrade.Downloader;
import org.msk86.ygoroid.upgrade.UpgradeHelper;
import org.msk86.ygoroid.upgrade.UpgradeMsgHandler;
import org.msk86.ygoroid.views.OnKeyProcessor;
import org.msk86.ygoroid.views.OnMenuProcessor;
import org.msk86.ygoroid.views.YGOView;
import org.msk86.ygoroid.views.deckbuilder.DeckBuilderView;
import org.msk86.ygoroid.views.logo.LogoView;
import org.msk86.ygoroid.views.newdueldisk.DuelDiskView;
import org.msk86.ygoroid.views.sidechanger.SideChangerView;

public class YGOActivity extends Activity {
    private View currentView;
    private DuelDiskView duelDiskView;
    private SideChangerView sideChangerView;
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
    private boolean newestDatabase = false;
    private boolean wifiChecked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        org.msk86.ygoroid.utils.Utils.initInstance(this);
        org.msk86.ygoroid.newutils.Utils.initInstance(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initWebView();

        if (savedInstanceState != null) {
            if (YGOView.DUEL_STATE_DECK.equals(savedInstanceState.getString(DUEL_STATE))) {
                showDeckBuilderWithDeck(org.msk86.ygoroid.newutils.Utils.findTempDeck());
            } else if(YGOView.DUEL_STATE_SIDE.equals(savedInstanceState.getString(DUEL_STATE))) {
                showSideChanger();
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
        webView.loadUrl(org.msk86.ygoroid.utils.Utils.s(R.string.hint));
        currentView = webView;
        setContentView(webView);
    }

    public void showFeedback() {
        webView.loadUrl(org.msk86.ygoroid.utils.Utils.s(R.string.feedback));
        currentView = webView;
        setContentView(webView);
    }

    public void showLogo() {
        logoView = new LogoView(this);
        super.setContentView(logoView);
        currentView = logoView;
    }

    public void showDuel() {
        super.setContentView(R.layout.new_duel_disk);
        duelDiskView = (DuelDiskView) findViewById(R.id.newDuelDiskView);
        if (!duelDiskView.isRunning()) {
            duelDiskView.resume();
        }
        currentView = duelDiskView;
        duelDiskView.updateActionTime();
    }

    public void showDuelWithDeck(String deck) {
        showDuel();
        if (deck != null) {
            duelDiskView.getDuel().start(deck);
        }
        duelDiskView.updateActionTime();
    }

    public void showDuelWithDeck(DeckCards deck) {
        showDuel();
        if (deck != null) {
            duelDiskView.getDuel().start(deck);
        }
        duelDiskView.updateActionTime();
    }

    public void showSideChanger() {
        super.setContentView(R.layout.side_changer);
        sideChangerView = (SideChangerView) findViewById(R.id.sideChangerView);
        if (!sideChangerView.isRunning()) {
            sideChangerView.resume();
        }
        currentView = sideChangerView;
        sideChangerView.updateActionTime();
    }

    public void showSideChangerWithDeck(DeckCards cards) {
        showSideChanger();
        if (cards != null) {
            sideChangerView.loadDeck(cards);
        }
        sideChangerView.updateActionTime();
    }

    public void showDeckBuilderWithDeck(String deck) {
        super.setContentView(R.layout.deck_builder);
        deckBuilderView = (DeckBuilderView) findViewById(R.id.deck_builder);
        if (!deckBuilderView.isRunning()) {
            deckBuilderView.resume();
        }
        currentView = deckBuilderView;
        if (deck != null) {
            deckBuilderView.loadDeck(deck);
        }
        deckBuilderView.updateActionTime();
    }

    public boolean isMirror() {
        return mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(currentView instanceof YGOView) {
            OnMenuProcessor onMenuProcessor = ((YGOView) currentView).getOnMenuProcessor();
            if(onMenuProcessor != null) {
                return onMenuProcessor.onMenuPrepare(menu);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int id, MenuItem menuItem) {
        if(currentView instanceof YGOView) {
            OnMenuProcessor onMenuProcessor = ((YGOView) currentView).getOnMenuProcessor();
            if(onMenuProcessor != null) {
                return onMenuProcessor.onMenuClick(menuItem);
            }
        }
        return super.onMenuItemSelected(id, menuItem);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(currentView instanceof YGOView) {
            OnKeyProcessor onKeyProcessor = ((YGOView) currentView).getOnKeyProcessor();
            if(onKeyProcessor != null) {
                return onKeyProcessor.onKey(keyCode, event);
            }
        } else {
            if (currentView == webView) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    showDuel();
                    return true;
                }
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
            duelDiskView.getDuel().getInfoBar().setInfo(info);
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
        if (currentView instanceof YGOView) {
            String duelState = ((YGOView) currentView).getDuelState();
            outState.putString(DUEL_STATE, duelState);
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
            if(service != null && currentView instanceof YGOView) {
                YGOView view = (YGOView) currentView;
                Item data = service.fetchItem(view);
                if(data != null) {
                    view.importData(data);
                } else {
                    service.storeItem(view, view.exportData());
                }
            }

            if (service != null && downloader != null) {
                service.setDownloader(downloader);
            }
        }
    }

}

