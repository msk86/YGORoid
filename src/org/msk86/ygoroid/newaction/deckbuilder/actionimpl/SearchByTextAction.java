package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.widget.TextView;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;
import org.msk86.ygoroid.views.newdeckbuilder.filter.CardFilter;

import java.util.ArrayList;
import java.util.List;

public class SearchByTextAction implements Action {
    DeckBuilderView deckBuilderView;
    TextView textView;

    public SearchByTextAction(DeckBuilderView deckBuilderView, TextView textView) {
        this.deckBuilderView = deckBuilderView;
        this.textView = textView;
    }

    @Override
    public void execute() {
        String text = textView.getText().toString();
        if (text.length() == 0) {
            deckBuilderView.getSearchResultList().clear();
            textView.setText("");
            return;
        }
        List<Card> cards = Utils.getDbHelper().queryByText(text, new ArrayList<CardFilter>());

        deckBuilderView.getSearchResultList().clear();
        deckBuilderView.getSearchResultList().addResult(cards, text);
        deckBuilderView.getSearchResultList().refresh();
    }
}
