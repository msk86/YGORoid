package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newop.Operation;

public abstract class BaseAction implements Action {
    protected Operation operation;

    protected Duel duel;
    protected Container container;
    protected Item item;

    protected BaseAction(Operation operation) {
        this(operation.getDuel(), operation.getContainer(), operation.getItem());
        this.operation = operation;
    }

    protected BaseAction(Duel duel, Container container, Item item) {
        this.duel = duel;
        this.container = container;
        this.item = item;
    }
}
