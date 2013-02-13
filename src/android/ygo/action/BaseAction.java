package android.ygo.action;

import android.ygo.core.Duel;
import android.ygo.core.Item;
import android.ygo.core.SelectableItem;
import android.ygo.op.Operation;

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
