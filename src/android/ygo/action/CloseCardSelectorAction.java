package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.core.CardSelector;
import android.ygo.op.Operation;

public class CloseCardSelectorAction extends BaseAction {
    public CloseCardSelectorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        CardSelector selector = duel.getCardSelector();
        CardList list = selector.getCardList();

        String listName = list.getName();
        if(!listName.equals("TEMPORARY") && !listName.equals("REMOVED")) {
            if(list.isOpen()) {
                list.openAll();
            } else {
                list.setAll();
            }
        }

        duel.setCardSelector(null);
        duel.select(selector.getSourceItem());
    }
}
