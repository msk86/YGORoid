package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.LpDisplayRenderer;

public class LpDisplay implements Item, Selectable {
    int lifePoint;
    String name;
    int index;
    Operator operator;
    String number;
    boolean select;

    public LpDisplay(int lifePoint, String name, int index) {
        this.lifePoint = lifePoint;
        this.name = name;
        this.index = index;
        number = "0";
        operator = Operator.MINUS;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getNumber() {
        return number;
    }

    public int getIndex() {
        return index;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public String getName() {
        return name;
    }

    public void appendNumber(String number) {
        if("0".equals(this.number)) {
            if("0".equals(number) || "00".equals(number)) {
                return;
            }
            this.number = number;
        } else {
            String toBe = this.number + number;
            this.number = toBe.substring(0, Math.min(5, toBe.length()));
        }
    }

    public void clearNumber() {
        this.number = "0";
    }

    public int getResult() {
        return operator.operate(lifePoint, Integer.parseInt(this.number));
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new LpDisplayRenderer(this);
        }
        return renderer;
    }

    @Override
    public void select() {
        select = true;
    }

    @Override
    public void unSelect() {
        select = false;
    }

    @Override
    public boolean isSelect() {
        return select;
    }
}
