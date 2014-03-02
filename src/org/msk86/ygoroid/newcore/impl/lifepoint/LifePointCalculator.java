package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.LifePoint;

public class LifePointCalculator implements Item {
    LifePoint lifePoint;

    boolean isCalculatingOpponent;
    int calcValue;
    Operator currentOperator;

    public LifePointCalculator(LifePoint lifePoint) {
        this.lifePoint = lifePoint;
        isCalculatingOpponent = false;
        calcValue = 0;
        currentOperator = Operator.MINUS;
    }

    @Override
    public Renderer getRenderer() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
