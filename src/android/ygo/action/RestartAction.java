package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.core.Field;
import android.ygo.core.Overlay;
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
