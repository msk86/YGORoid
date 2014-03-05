package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.OperatorRenderer;

public enum Operator implements Item {
    PLUS("+") {
        public int operate(int v1, int v2) {
            return v1 + v2;
        }
    },
    MINUS("-") {
        public int operate(int v1, int v2) {
            return v1 - v2;
        }
    },
    DIVIDE("/"){
        public int operate(int v1, int v2) {
            if(v2 == 0) {
                return v1;
            }
            return v1 / v2;
        }
    };

    String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public int operate(int v1, int v2) {
        return this.operate(v1, v2);
    }

    @Override
    public String toString() {
        return operator;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new OperatorRenderer(this);
        }
        return renderer;
    }
}
