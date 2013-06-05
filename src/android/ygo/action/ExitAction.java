package android.ygo.action;

import android.ygo.op.Operation;

public class ExitAction extends BaseAction {
    public ExitAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
