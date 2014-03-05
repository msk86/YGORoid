package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.lifepoint.LifePointCalculator;
import org.msk86.ygoroid.newcore.impl.lifepoint.Operator;
import org.msk86.ygoroid.newop.Operation;

public class ChangeLpOperatorAction extends BaseAction {
    public ChangeLpOperatorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if (item instanceof Operator) {
            Operator operator = (Operator) item;
            LifePointCalculator calculator = duel.getLifePointCalculator();
            calculator.getSelectedLpDisplay().clearNumber();
            if(operator == Operator.DIVIDE) {
                calculator.getSelectedLpDisplay().appendNumber("2");
            }
            calculator.getSelectedLpDisplay().setOperator(operator);
        }
    }
}
