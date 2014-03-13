package org.msk86.ygoroid.newcore.impl.side.renderer;

import android.graphics.Canvas;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.side.SideChanger;
import org.msk86.ygoroid.size.SideChangerSize;
import org.msk86.ygoroid.size.Size;

public class SideChangerRenderer implements Renderer {
    SideChanger changer;

    public SideChangerRenderer(SideChanger changer) {
        this.changer = changer;
    }

    @Override
    public Size size() {
        return SideChangerSize.TOTAL;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);

        for(Item item : changer.getLayout().items()) {
            Point point = changer.getLayout().itemPosition(item);
            item.getRenderer().draw(canvas, point.x, point.y);
        }

        canvas.restore();
    }
}
