package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Controllable;
import org.msk86.ygoroid.newop.Operation;

public class FlipCardAction extends BaseAction {
    public FlipCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item  instanceof Controllable) {
            ((Controllable) item).flip();
        }
    }
}
