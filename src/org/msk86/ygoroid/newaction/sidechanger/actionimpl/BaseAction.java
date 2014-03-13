package org.msk86.ygoroid.newaction.sidechanger.actionimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newcore.impl.side.SideChanger;
import org.msk86.ygoroid.newop.Operation;

public abstract class BaseAction implements Action {
    protected Operation operation;

    protected SideChanger sideChanger;
    protected Container container;
    protected Item item;

    protected BaseAction(Operation operation) {
        this((SideChanger)operation.getBaseContainer(), operation.getContainer(), operation.getItem());
        this.operation = operation;
    }

    protected BaseAction(SideChanger sideChanger, Container container, Item item) {
        this.sideChanger = sideChanger;
        this.container = container;
        this.item = item;
    }
}
