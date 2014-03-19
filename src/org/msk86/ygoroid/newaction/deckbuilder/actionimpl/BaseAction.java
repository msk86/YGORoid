package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.builder.DeckBuilder;
import org.msk86.ygoroid.newop.Operation;

public abstract class BaseAction implements Action {
    protected Operation operation;

    protected DeckBuilder deckBuilder;
    protected Container container;
    protected Item item;

    protected BaseAction(Operation operation) {
        this((DeckBuilder)operation.getBaseContainer(), operation.getContainer(), operation.getItem());
        this.operation = operation;
    }

    protected BaseAction(DeckBuilder deckBuilder, Container container, Item item) {
        this.deckBuilder = deckBuilder;
        this.container = container;
        this.item = item;
    }
}
