package org.msk86.ygoroid.views.deckbuilder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.UserDefinedCard;
import org.msk86.ygoroid.utils.Utils;
import org.msk86.ygoroid.views.deckbuilder.filter.CardFilter;

import java.util.ArrayList;
import java.util.List;

public class CardNameList {

    DeckBuilderView deckBuilderView;
    Card selectedCard;
    List<Card> searchResult;
    String searchText;

    public CardNameList(DeckBuilderView deckBuilderView) {
        this.deckBuilderView = deckBuilderView;
        this.searchResult = new ArrayList<Card>();
        this.searchText = "";
    }

    public void clearList() {
        searchResult.clear();
        LinearLayout cardList = (LinearLayout)Utils.getContext().findViewById(R.id.card_list);
        cardList.removeAllViews();
        selectedCard = null;
        ScrollView scrollList = (ScrollView) Utils.getContext().findViewById(R.id.scroll_list);
        scrollList.setBackgroundDrawable(null);
    }

    public void search(String text) {
        search(text, new ArrayList<CardFilter>());
    }

    public void search(List<CardFilter> filters) {
        search(searchText, filters);
    }

    public void search(String text, List<CardFilter> filters) {
        if(text.length() == 0) {
            boolean filtersNotValid = true;
            for(CardFilter filter : filters) {
                filtersNotValid &= !filter.isValid();
            }
            if(filtersNotValid) {
                clearList();
                searchText = "";
                return;
            }
        }
        this.searchText = text;
        List<Card> cards = Utils.getDbHelper().queryByText(text, filters);
        if (!checkExactQuery(cards, text)) {
            cards.add(new UserDefinedCard(text));
        }
        clearList();
        searchResult.addAll(cards);
        show();
    }

    private void show() {
        LinearLayout cardList = (LinearLayout) Utils.getContext().findViewById(R.id.card_list);
        for(Card card : searchResult) {
            CardNameView cv = new CardNameView(Utils.getContext(), card);
            cv.setOnClickListener(new OnClickCardNameListener());
            cardList.addView(cv);
        }
    }

    private boolean checkExactQuery(List<Card> cards, String text) {
        boolean exact = false;
        if(text.length() == 0 ){
            exact = true;
        }
        for(Card card : cards) {
            if (card.getName().equals(text)) {
                exact = true;
                break;
            }
        }
        return exact;
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

            clearSelect();

            cardNameView.setSelected(true);
            selectedCard = cardNameView.getCard();
            deckBuilderView.select(selectedCard);
            deckBuilderView.updateActionTime();

            ScrollView scrollList = (ScrollView) Utils.getContext().findViewById(R.id.scroll_list);

            scrollList.setBackgroundDrawable(getCardPicDrawable(selectedCard, scrollList.getWidth(), scrollList.getHeight()));
        }

        private Drawable getCardPicDrawable(Card card, int w, int h) {
            Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
            Bitmap cardBmp = card.bmp(Utils.cardPreviewWidth(), Utils.cardPreviewHeight());
            helper.drawBitmap(canvas, cardBmp, 0, helper.center(h, Utils.cardPreviewHeight()), new Paint());
            canvas.drawARGB(60, 0, 0, 0);
            cardBmp.recycle();
            return new BitmapDrawable(bmp);
        }
    }

    public void clearSelect() {
        LinearLayout cardList = (LinearLayout)Utils.getContext().findViewById(R.id.card_list);
        for(int i=0;i<cardList.getChildCount();i++) {
            View cv = cardList.getChildAt(i);
            cv.setSelected(false);
        }
    }
}
