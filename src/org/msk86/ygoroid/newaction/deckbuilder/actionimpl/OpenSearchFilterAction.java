package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;
import org.msk86.ygoroid.views.newdeckbuilder.SearchFilter;

public class OpenSearchFilterAction implements Action {
    private static SearchFilter filter;
    DeckBuilderView deckBuilderView;

    public OpenSearchFilterAction(DeckBuilderView deckBuilderView) {
        this.deckBuilderView = deckBuilderView;
        if(filter == null) {
            filter = new SearchFilter(deckBuilderView);
        }
    }

    @Override
    public void execute() {
        filter.clear();
        filter.show();
    }
}
