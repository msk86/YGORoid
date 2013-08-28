package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Duel;
import org.msk86.ygoroid.core.Item;
import org.msk86.ygoroid.core.SelectableItem;
import org.msk86.ygoroid.op.Operation;

public abstract class BaseAction implements Action {
    protected Operation operation;

    protected Duel duel;
    protected Item container;
    protected SelectableItem item;

    protected BaseAction(Operation operation) {
        this(operation.getDuel(), operation.getContainer(), operation.getItem());
        this.operation = operation;
    }

    protected BaseAction(Duel duel, Item container, SelectableItem item) {
        this.duel = duel;
        this.container = container;
        this.item = item;
    }
}
