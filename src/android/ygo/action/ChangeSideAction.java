package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.SideWindow;
import android.ygo.layout.Layout;
import android.ygo.op.Operation;

public class ChangeSideAction extends BaseAction {
    public ChangeSideAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.select(item, container);
        SideWindow sideWindow = duel.getSideWindow();

        sideWindow.setSelectCard((Card) item, (Layout) container);

        Card card1 = sideWindow.getSelectCardMain();
        Layout layout1 = sideWindow.getSelectLayoutMain();

        Card card2 = sideWindow.getSelectCardSide();
        Layout layout2 = sideWindow.getSelectLayoutSide();

        if(card1 != null && card2 != null) {
            layout1.cards().remove(card1);
            layout2.cards().remove(card2);

            if(card2.isEx()) {
                sideWindow.getExLayout().cards().add(card2);
            } else {
                sideWindow.getMainLayout().cards().add(card2);
            }
            layout2.cards().add(card1);

            sideWindow.clearSelect();
        }
    }
}
