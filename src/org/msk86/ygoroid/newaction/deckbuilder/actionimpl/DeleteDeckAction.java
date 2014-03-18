package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;

public class DeleteDeckAction implements Action {
    DeckBuilderView deckBuilderView;

    public DeleteDeckAction(DeckBuilderView deckBuilderView) {
        this.deckBuilderView = deckBuilderView;
    }

    @Override
    public void execute() {

    }
}
