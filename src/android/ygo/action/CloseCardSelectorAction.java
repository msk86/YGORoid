package android.ygo.action;

import android.ygo.R;
import android.ygo.core.CardList;
import android.ygo.core.CardSelector;
import android.ygo.op.Operation;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

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
