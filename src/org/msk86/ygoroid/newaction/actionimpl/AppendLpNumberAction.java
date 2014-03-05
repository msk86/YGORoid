package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.lifepoint.LifePointCalculator;
import org.msk86.ygoroid.newcore.impl.lifepoint.Numbers;
import org.msk86.ygoroid.newop.Operation;

public class AppendLpNumberAction extends BaseAction {
    public AppendLpNumberAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof Numbers) {
            Numbers numbers = (Numbers) item;
            LifePointCalculator calculator = duel.getLifePointCalculator();
            calculator.getSelectedLpDisplay().appendNumber(numbers.getValue());
        }
    }
}
