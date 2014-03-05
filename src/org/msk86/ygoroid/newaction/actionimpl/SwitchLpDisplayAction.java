package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.lifepoint.LifePointCalculator;
import org.msk86.ygoroid.newcore.impl.lifepoint.LpDisplay;
import org.msk86.ygoroid.newop.Operation;

public class SwitchLpDisplayAction extends BaseAction {
    public SwitchLpDisplayAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof LpDisplay) {
            LifePointCalculator calculator = duel.getLifePointCalculator();
            calculator.selectLpDisplay((LpDisplay) item);
        }
    }
}
