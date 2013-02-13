package android.ygo.action;

import android.util.Log;
import android.ygo.op.Operation;

public class OpenInfoAction extends BaseAction {

    public OpenInfoAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Log.e("YGO", "Open Info Action!");
    }
}
