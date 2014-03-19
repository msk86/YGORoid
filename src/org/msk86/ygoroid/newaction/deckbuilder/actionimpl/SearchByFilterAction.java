package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;
import org.msk86.ygoroid.views.newdeckbuilder.SearchFilter;
import org.msk86.ygoroid.views.newdeckbuilder.SearchResultList;
import org.msk86.ygoroid.views.newdeckbuilder.filter.CardFilter;

import java.util.List;

public class SearchByFilterAction implements Action {
    DeckBuilderView deckBuilderView;
    SearchFilter searchFilter;

    public SearchByFilterAction(DeckBuilderView deckBuilderView, SearchFilter searchFilter) {
        this.deckBuilderView = deckBuilderView;
        this.searchFilter = searchFilter;
    }

    @Override
    public void execute() {
        SearchResultList searchResult = deckBuilderView.getSearchResultList();
        List<CardFilter> filters = searchFilter.genFilters();

        searchResult.clear();

        boolean filtersNotValid = true;
        for (CardFilter filter : filters) {
            filtersNotValid &= !filter.isValid();
        }
        if (filtersNotValid) {
            return;
        }
        List<Card> cards = Utils.getDbHelper().queryByText("", filters);
        searchResult.addResult(cards, "");
        searchResult.refresh();
    }
}
