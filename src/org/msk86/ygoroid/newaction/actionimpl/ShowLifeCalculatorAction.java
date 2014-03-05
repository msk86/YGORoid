package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.lifepoint.LifePointCalculator;
import org.msk86.ygoroid.newop.Operation;

public class ShowLifeCalculatorAction extends BaseAction {
    public ShowLifeCalculatorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.setLifePointCalculator(new LifePointCalculator(duel.getLifePoint()));
    }
}
