package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.LifePoint;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.LifePointCalculatorRenderer;

public class LifePointCalculator implements Item, Container {
    LifePoint lifePoint;

    LpDisplay[] lpDisplays = new LpDisplay[2];

    OperationPad operationPad;
    NumberPad numberPad;

    Button okButton;
    Button cancelButton;

    public LifePointCalculator(LifePoint lifePoint) {
        this.lifePoint = lifePoint;

        lpDisplays[0] = new LpDisplay(lifePoint.getLp(0), "LP-1", 0);
        lpDisplays[1] = new LpDisplay(lifePoint.getLp(1), "LP-2", 1);
        lpDisplays[0].select();

        operationPad = new OperationPad();
        numberPad = new NumberPad();

        okButton = Button.OK;
        cancelButton = Button.CANCEL;
    }

    public LifePoint getLifePoint() {
        return lifePoint;
    }

    public LpDisplay getLpDisplay(int idx) {
        return lpDisplays[idx];
    }

    public OperationPad getOperationPad() {
        return operationPad;
    }

    public NumberPad getNumberPad() {
        return numberPad;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public LpDisplay getSelectedLpDisplay() {
        return lpDisplays[0].isSelect() ? lpDisplays[0] : lpDisplays[1];
    }

    public void selectLpDisplay(LpDisplay lpDisplay) {
        for(LpDisplay display : lpDisplays) {
            display.unSelect();
        }
        lpDisplay.select();
    }

    public void calculate() {
        getLifePoint().setLp(getLpDisplay(0).getResult(), 0);
        getLifePoint().setLp(getLpDisplay(1).getResult(), 1);
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new LifePointCalculatorRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new AbsoluteLayout(this);
        }
        return layout;
    }
}
