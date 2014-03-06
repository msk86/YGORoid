package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class ClearLpNumberAction extends BaseAction {
    public ClearLpNumberAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.getLifePointCalculator().getSelectedLpDisplay().clearNumber();
    }
}
