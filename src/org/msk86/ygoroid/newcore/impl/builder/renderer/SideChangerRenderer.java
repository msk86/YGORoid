package org.msk86.ygoroid.newcore.impl.builder.renderer;

import android.graphics.Canvas;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newcore.impl.builder.SideChanger;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.size.BuilderSize;
import org.msk86.ygoroid.size.InfoBarSize;
import org.msk86.ygoroid.size.Size;

public class SideChangerRenderer implements Renderer {
    SideChanger changer;

    public SideChangerRenderer(SideChanger changer) {
        this.changer = changer;
        initLayout();
    }

    private void initLayout() {
        AbsoluteLayout layout = (AbsoluteLayout) changer.getLayout();

        layout.addItem(changer.getMainDeckSection(), 0, 0, 0);
        layout.addItem(changer.getExDeckSection(), 0, BuilderSize.MAIN_SECTION.height() + Style.padding(), 0);
        layout.addItem(changer.getSideDeckSection(), 0, BuilderSize.MAIN_SECTION.height() + BuilderSize.EX_SECTION.height() + Style.padding() * 2, 0);
        layout.addItem(changer.getInfoBar(), 0, size().height() - InfoBarSize.INFO_BAR.height(), 1);

    }

    private void updateLayoutWithWindow() {
        AbsoluteLayout layout = (AbsoluteLayout) changer.getLayout();

        if(changer.getCardEffectWindow() != null) {
            layout.addItem(changer.getCardEffectWindow(), 0, 0, 2);
        }  else {
            layout.removeItems(CardEffectWindow.class);
        }
    }

    @Override
    public Size size() {
        return BuilderSize.SIDER;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        updateLayoutWithWindow();

        canvas.save();
        canvas.translate(x, y);

        for(Item item : changer.getLayout().items()) {
            Point point = changer.getLayout().itemPosition(item);
            item.getRenderer().draw(canvas, point.x, point.y);
        }

        canvas.restore();
    }
}
