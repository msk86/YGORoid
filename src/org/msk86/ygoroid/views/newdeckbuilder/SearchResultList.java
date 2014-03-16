package org.msk86.ygoroid.views.newdeckbuilder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.UserDefinedCard;
import org.msk86.ygoroid.newcore.impl.builder.DeckBuilder;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.filter.CardFilter;

import java.util.ArrayList;
import java.util.List;

public class SearchResultList {
    DeckBuilder deckBuilder;
    List<Card> searchResult;
    Card selectedCard;

    public SearchResultList(DeckBuilder deckBuilder) {
        this.deckBuilder = deckBuilder;
        this.searchResult = new ArrayList<Card>();
    }

    public void clear() {
        searchResult.clear();
        LinearLayout cardList = (LinearLayout) Utils.getContext().findViewById(R.id.card_list);
        cardList.removeAllViews();
        selectedCard = null;
        ScrollView scrollList = (ScrollView) Utils.getContext().findViewById(R.id.scroll_list);
        scrollList.setBackgroundDrawable(null);
        scrollList.scrollTo(0, 0);
    }

    public void addResult(List<Card> cards) {
        searchResult.addAll(cards);
    }

    public void refresh() {
        LinearLayout cardList = (LinearLayout) Utils.getContext().findViewById(R.id.card_list);
        for (Card card : searchResult) {
            CardNameView cv = new CardNameView(Utils.getContext(), card);
            cv.setOnClickListener(new OnClickCardNameListener());
            cardList.addView(cv);
        }
    }

    private class OnClickCardNameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            List<Action> actionChain = new ArrayList<Action>();

            CardNameView cardNameView = (CardNameView) view;
            if (cardNameView.isSelected()) {
//                actionChain.add(new AddCardToDeckAction());
            } else {
//                actionChain.add(new SelectSearchResultAction());
            }

            for(Action action:actionChain) {
                action.execute();
            }
        }
    }
}
