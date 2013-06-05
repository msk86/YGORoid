package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.core.Field;
import android.ygo.core.OverRay;
import android.ygo.op.Operation;

public class DiceAction extends BaseAction {
    public DiceAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.getDice().throwDice();
    }
}
