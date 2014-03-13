package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newop.Operation;

public class SelectAction extends BaseAction {
    public SelectAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof Selectable) {
            duel.select((Selectable) item);
        }
    }
}
