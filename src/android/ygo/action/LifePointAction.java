package android.ygo.action;

import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class LifePointAction extends BaseAction {
    public LifePointAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.getLifePoint().showEditDialog();
    }
}
