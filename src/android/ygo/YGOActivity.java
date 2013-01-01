package android.ygo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.ygo.utils.Utils;
import android.ygo.views.DuelDiskView;

public class YGOActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.initInstance(this);

        setContentView(new DuelDiskView(this));
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :

        }
        return false;
    }
}
