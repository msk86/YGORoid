package org.msk86.ygoroid.action;

import android.content.pm.ActivityInfo;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

public class MirrorDisplayAction extends BaseAction {
    public MirrorDisplayAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
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
