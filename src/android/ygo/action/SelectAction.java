package android.ygo.action;

import android.ygo.op.Operation;

public class SelectAction extends BaseAction {

    public SelectAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.select(item, container);
    }
}
