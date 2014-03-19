package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;


import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newop.Operation;

public class ShowNextPageDescAction extends BaseAction {
    public ShowNextPageDescAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof CardEffectWindow) {
            ((CardEffectWindow) item).nextPage();
        }
    }
}
