package android.ygo.views.deckbuilder;

import android.view.View;
import android.widget.LinearLayout;
import android.ygo.R;
import android.ygo.core.Card;
import android.ygo.utils.Utils;

import java.util.List;

public class CardNameList {

    DeckBuilderView deckBuilderView;
    Card selectedCard;

    public CardNameList(DeckBuilderView deckBuilderView) {
        this.deckBuilderView = deckBuilderView;
    }

    public void clearList() {
        LinearLayout cardList = (LinearLayout)Utils.getContext().findViewById(R.id.card_list);
        cardList.removeAllViews();
        selectedCard = null;
    }

    public void search(String text) {
        List<Card> cards = Utils.getDbHelper().queryByText(text);
        clearList();
        addAll(cards);
    }

    private void add(Card card) {
        LinearLayout cardList = (LinearLayout)Utils.getContext().findViewById(R.id.card_list);
        CardNameView cv = new CardNameView(Utils.getContext(), card);
        cv.setOnClickListener(new OnClickCardNameListener());
        cardList.addView(cv);
    }

    private void addAll(List<Card> cards) {
        for(Card card : cards) {
            add(card);
        }
    }

    private class OnClickCardNameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            CardNameView cardNameView = (CardNameView) view;
            if(cardNameView.isSelected()) {
                if(selectedCard != null) {
                    deckBuilderView.addToDeck(selectedCard);
                }
                return;
            }

            LinearLayout cardList = (LinearLayout)Utils.getContext().findViewById(R.id.card_list);
            for(int i=0;i<cardList.getChildCount();i++) {
                View cv = cardList.getChildAt(i);
                cv.setSelected(false);
            }

            cardNameView.setSelected(true);
            selectedCard = cardNameView.getCard();
        }
    }
}
