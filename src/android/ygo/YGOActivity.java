package android.ygo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.ygo.exception.CrashHandler;
import android.ygo.utils.Utils;
import android.ygo.views.DuelDiskView;
import android.ygo.views.PlayMenuProcessor;
import android.ygo.views.PlayOnKeyProcessor;

public class YGOActivity extends Activity {
    private PlayOnKeyProcessor keyProcessor;
    private PlayMenuProcessor menuProcessor;
    private DuelDiskView duelDiskView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        Utils.initInstance(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.main);
        duelDiskView = (DuelDiskView) findViewById(R.id.duelDiskView);

        keyProcessor = new PlayOnKeyProcessor(duelDiskView);
        menuProcessor = new PlayMenuProcessor(duelDiskView);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return menuProcessor.onMenuPrepare(menu);
    }

    @Override
    public boolean onMenuItemSelected(int id, MenuItem menuItem) {
        return menuProcessor.onMenuClick(menuItem);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyProcessor.onKey(keyCode, event);
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

    public DuelDiskView getDuelDiskView() {
        return duelDiskView;
    }
}

