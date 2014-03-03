package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.NumberRenderer;

public enum Numbers implements Item {
    N1("1"), N2("2"), N3("3"), N4("4"), N5("5"), N6("6"), N7("7"), N8("8"), N9("9"), N0("0"), N00("00");

    String value;

    private Numbers(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    Renderer renderer;

    @Override
    public Renderer getRenderer() {
        if (renderer == null) {
            renderer = new NumberRenderer(this);
        }
        return renderer;
    }
}
