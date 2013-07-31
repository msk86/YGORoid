package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.core.CardSelector;
import android.ygo.op.Operation;
import android.ygo.utils.Configuration;

public class CloseCardSelectorAction extends BaseAction {
    public CloseCardSelectorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        CardSelector selector = duel.getCardSelector();
        CardList list = selector.getCardList();

        String listName = list.getName();
        if (!listName.equals(CardList.TEMPORARY) && !listName.equals(CardList.REMOVED)) {
            if (list.isOpen()) {
                list.openAll();
            } else {
                list.setAll();
            }
        }

        if(listName.equals(CardList.DECK) && Configuration.configProperties(Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE)) {
            list.shuffle();
        }

        duel.setCardSelector(null);
        duel.select(selector.getSourceItem(), container);
    }
}
