package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.SideWindow;
import org.msk86.ygoroid.core.UserDefinedCard;
import org.msk86.ygoroid.layout.Layout;
import org.msk86.ygoroid.op.Operation;

public class ChangeSideAction extends BaseAction {
    public ChangeSideAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        SideWindow sideWindow = duel.getSideWindow();

        Card card1 = sideWindow.getSelectCard();
        Layout layout1 = sideWindow.getSelectLayout();

        Card card2 = (Card) item;
        Layout layout2 = (Layout) container;

        if (layout1 == layout2) {
            return;
        }
        if (layout1 != sideWindow.getSideLayout() && layout2 != sideWindow.getSideLayout()) {
            return;
        }

        if (card1 != null && card2 != null) {
            if (card1.isEx() != card2.isEx()
                    && !(card1 instanceof UserDefinedCard)
                    && !(card2 instanceof UserDefinedCard)) {
                return;
            }

            layout1.cards().remove(card1);
            layout2.cards().remove(card2);

            layout1.cards().add(card2);
            layout2.cards().add(card1);

            sideWindow.clearSelect();
            duel.unSelect();
        }
    }
}
