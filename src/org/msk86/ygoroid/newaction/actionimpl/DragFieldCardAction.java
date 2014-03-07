package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newop.impl.StartDrag;

public class DragFieldCardAction extends BaseAction {
    public DragFieldCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Item item = ((Field) container).removeItem();
        ((StartDrag) operation).setDragItem(item);
    }
}
