package android.ygo.upgrade;

import android.os.Handler;
import android.os.Message;
import android.ygo.YGOActivity;

public class UpgradeMsgHandler extends Handler {

    YGOActivity context;

    public UpgradeMsgHandler(YGOActivity context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        context.getUpgradeHelper().alertUpgrade();
        super.handleMessage(msg);
    }
}
