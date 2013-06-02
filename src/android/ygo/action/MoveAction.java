package android.ygo.action;

import android.ygo.core.Field;
import android.ygo.op.Operation;

public class MoveAction extends BaseAction {

    public MoveAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        ((Field) container).setItem(item);
        duel.select(item, container);
    }
}
