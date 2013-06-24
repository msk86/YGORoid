package android.ygo.action;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.ygo.YGOActivity;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

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
