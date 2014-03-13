package org.msk86.ygoroid.newaction.sidechanger.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newop.Operation;

public class SelectAction extends BaseAction {
    public SelectAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof Card) {
            sideChanger.select((Card) item);
        }
    }
}
