package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class CloseCardEffectWindowAction extends BaseAction {
    public CloseCardEffectWindowAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.setCardEffectWindow(null);
    }
}
