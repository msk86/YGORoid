package android.ygo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.ygo.utils.Utils;
import android.ygo.views.DuelDiskView;
import android.ygo.views.PlayOnKeyProcessor;

public class YGOActivity extends Activity {
    private PlayOnKeyProcessor keyProcessor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.initInstance(this);

        setContentView(R.layout.main);
        DuelDiskView duelDiskView = (DuelDiskView)findViewById(R.id.duelDiskView);
        duelDiskView.resume();
        keyProcessor = new PlayOnKeyProcessor(duelDiskView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyProcessor.onKey(keyCode, event);
    }
}

