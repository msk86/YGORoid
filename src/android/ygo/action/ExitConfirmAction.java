package android.ygo.action;

import android.ygo.op.Operation;

public class ExitConfirmAction extends BaseAction {
    public ExitConfirmAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
    	duel.getExitConfirm().showEditDialog();
    }
}
