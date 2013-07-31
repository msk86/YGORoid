package android.ygo.action;

import android.ygo.op.Operation;

public class CloseCardWindowAction extends BaseAction {
    public CloseCardWindowAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.setCardWindow(null);
    }
}
