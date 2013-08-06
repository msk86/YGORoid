package android.ygo.action;

import android.ygo.YGOActivity;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class OpenMenuAction extends BaseAction {
    public OpenMenuAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        YGOActivity activity = Utils.getContext();
        activity.openOptionsMenu();
    }
}
