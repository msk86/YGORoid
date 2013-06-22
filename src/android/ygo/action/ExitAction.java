package android.ygo.action;

import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class ExitAction extends BaseAction {
    public ExitAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Utils.getContext().stopService();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
