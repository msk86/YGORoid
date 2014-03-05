package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.ButtonRenderer;
import org.msk86.ygoroid.newutils.Utils;

public enum Button implements Item {
    CLEAR("C", 1), OK(Utils.s(R.string.CONFIRM_YES), 2), CANCEL(Utils.s(R.string.CONFIRM_NO), 2);

    private String text;
    private int size;

    private Button(String text, int size) {
        this.text = text;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return text;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new ButtonRenderer(this);
        }
        return renderer;
    }
}
