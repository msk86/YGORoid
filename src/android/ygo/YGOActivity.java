package android.ygo;

import android.app.Activity;
import android.os.Bundle;
import android.ygo.utils.Utils;
import android.ygo.views.DuelDiskView;

public class YGOActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Utils.initInstance(this);

        setContentView(new DuelDiskView(this));
    }
}
