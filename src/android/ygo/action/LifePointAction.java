package android.ygo.action;

import android.ygo.op.Operation;

public class LifePointAction extends BaseAction {
    public LifePointAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.getLifePoint().showEditDialog();
    }
}
