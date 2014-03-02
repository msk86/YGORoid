package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.ButtonRenderer;
import org.msk86.ygoroid.utils.Utils2;

public enum Button implements Item {
    CLEAR("C"), OK(Utils2.s(R.string.CONFIRM_YES)), CANCEL(Utils2.s(R.string.CONFIRM_NO));

    private String text;

    private Button(String text) {
        this.text = text;
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
