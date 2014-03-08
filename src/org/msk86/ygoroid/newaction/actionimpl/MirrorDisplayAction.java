package org.msk86.ygoroid.newaction.actionimpl;

import android.content.pm.ActivityInfo;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class MirrorDisplayAction extends BaseAction {
    public MirrorDisplayAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        YGOActivity activity = Utils.getContext();
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            activity.setMirror(true);
        } else if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            activity.setMirror(false);
        }
    }
}
