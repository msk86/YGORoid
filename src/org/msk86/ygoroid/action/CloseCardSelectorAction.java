package org.msk86.ygoroid.action;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.core.CardList;
import org.msk86.ygoroid.core.CardSelector;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

public class CloseCardSelectorAction extends BaseAction {
    public CloseCardSelectorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        CardSelector selector = duel.getCardSelector();
        CardList list = selector.getCardList();

        String listName = list.getName();
        if (!listName.equals(Utils.s(R.string.TEMPORARY)) && !listName.equals(Utils.s(R.string.REMOVED))) {
            if (list.isOpen()) {
                list.openAll();
            } else {
                list.setAll();
            }
        }

        if(listName.equals(Utils.s(R.string.DECK)) && Configuration.configProperties(Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE)) {
            list.shuffle();
        }

        duel.setCardSelector(null);
        duel.select(selector.getSourceItem(), container);
    }
}
