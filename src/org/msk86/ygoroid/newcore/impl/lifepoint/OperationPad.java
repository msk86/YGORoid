package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.layout.GridLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.OperationPadRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationPad implements Item, Container {
    List<Operator> operators;

    public OperationPad() {
        operators = new ArrayList<Operator>();
        Collections.addAll(operators, Operator.values());
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new OperationPadRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new GridLayout(this, operators);
        }
        return layout;
    }
}
