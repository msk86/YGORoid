package org.msk86.ygoroid.newaction.sidechanger.actionimpl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.UserDefinedCard;
import org.msk86.ygoroid.newcore.impl.side.SideDeckSection;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.LayoutUtils;

import java.util.List;

public class ChangeSideCardAction extends BaseAction {
    public ChangeSideCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card selected = (Card)sideChanger.getCurrentSelectItem();
        Container selectedFrom = LayoutUtils.itemContainer(sideChanger, selected);
        Card target = (Card) item;
        Container targetTo = container;

        if(selectedFrom == targetTo) {
            return;
        }
        if(!(selectedFrom instanceof SideDeckSection || targetTo instanceof SideDeckSection)) {
            return;
        }
        if(selected == null || target == null) {
            return;
        }
        if (selected.isEx() != target.isEx()
                && !(selected instanceof UserDefinedCard)
                && !(target instanceof UserDefinedCard)) {
            return;
        }

        List<Card> l1 = sideChanger.getCards().getDeckByCard(selected);
        List<Card> l2 = sideChanger.getCards().getDeckByCard(target);

        l1.remove(selected);
        l2.remove(target);

        l1.add(target);
        l2.add(selected);

        sideChanger.unSelect();
    }
}
