package android.ygo.action;

import android.ygo.op.Operation;

public class SideWindowFlipAction extends BaseAction {
    public SideWindowFlipAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.getSideWindow().flip();
    }
}
