package android.ygo.action;

import android.ygo.op.Operation;

public class RestartAction extends BaseAction {
    public RestartAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.initDuelField();
        duel.restart();
    }
}
