package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.Operation;

public class DragFieldCardAction extends BaseAction {
    public DragFieldCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        ((Field) container).removeItem();
    }
}
