package android.ygo.views.deckbuilder;

import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.ygo.R;
import android.ygo.core.Card;
import android.ygo.core.UserDefinedCard;
import android.ygo.utils.Configuration;
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
        if (!checkExactQuery(cards, text)) {
            cards.add(new UserDefinedCard(text));
        }
        clearList();
        addAll(cards);
    }

    private boolean checkExactQuery(List<Card> cards, String text) {
        boolean exact = false;
        for(Card card : cards) {
            if (card.getName().equals(text)) {
                exact = true;
                break;
            }
        }
        return exact;
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
            deckBuilderView.select(selectedCard);

            ScrollView scrollList = (ScrollView) Utils.getContext().findViewById(R.id.scroll_list);

            scrollList.setBackgroundDrawable(getCardPicDrawable(selectedCard));
        }

        private Drawable getCardPicDrawable(Card card) {
            Bitmap bmp = card.bmp(Utils.cardPreviewWidth(), Utils.cardPreviewHeight());
            Canvas canvas = new Canvas(bmp);
            canvas.drawARGB(60, 0, 0, 0);
            return new BitmapDrawable(bmp);
        }
    }
}
