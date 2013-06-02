package android.ygo.action;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;
import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.core.Field;
import android.ygo.core.Overlay;
import android.ygo.op.Operation;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class MirrorDisplayAction extends BaseAction {
    public MirrorDisplayAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Activity activity = Utils.getContext();
        if(activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        } else if(activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
