package org.msk86.ygoroid.upgrade;

import android.os.Handler;
import android.os.Message;
import org.msk86.ygoroid.YGOActivity;

public class UpgradeMsgHandler extends Handler {

    YGOActivity context;

    public UpgradeMsgHandler(YGOActivity context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Checker.UPGRADE:
                context.getUpgradeHelper().getUpgradeChecker().upgrade();
                break;
            case Checker.DATA_BASE:
                context.getUpgradeHelper().getDatabaseChecker().upgrade();
                break;
            case Checker.PICS:
                context.getUpgradeHelper().getPicChecker().upgrade();
                break;
        }

        super.handleMessage(msg);
    }
}
