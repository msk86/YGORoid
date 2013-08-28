package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;

public class DiceAction extends BaseAction {
    public DiceAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.getDice().throwDice();
    }
}
