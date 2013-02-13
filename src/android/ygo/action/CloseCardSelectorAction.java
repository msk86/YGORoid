package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.core.CardSelector;
import android.ygo.op.Operation;

public class CloseCardSelectorAction extends BaseAction {
    public CloseCardSelectorAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        CardSelector selector = duel.getCardSelector();
        CardList list = selector.getCardList();

        if(list.getName().equals("DECK") ||
                list.getName().equals("EX")) {
            list.setAll();
        }
        duel.setCardSelector(null);
        duel.select(selector.getSourceItem());


    }
}
