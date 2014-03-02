package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import org.msk86.ygoroid.newcore.impl.lifepoint.Operator;
import org.msk86.ygoroid.size.CalculatorSize;
import org.msk86.ygoroid.size.Size;

public class OperatorRenderer extends IconRenderer {
    private Operator operator;
    public OperatorRenderer(Operator operator) {
        this.operator = operator;
    }

    @Override
    public Size size() {
        return CalculatorSize.OPERATOR;
    }

    @Override
    public String getText() {
        return operator.toString();
    }

    @Override
    public int getTextFont() {
        return size().height() * 4 / 5;
    }
}
