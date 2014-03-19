package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ScrollView;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.views.newdeckbuilder.CardNameView;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;

public class SelectSearchResultAction implements Action {
    DeckBuilderView deckBuilderView;
    CardNameView cardNameView;

    public SelectSearchResultAction(DeckBuilderView deckBuilderView, CardNameView cardNameView) {
        this.deckBuilderView = deckBuilderView;
        this.cardNameView = cardNameView;
    }

    @Override
    public void execute() {
        deckBuilderView.getSearchResultList().clearSelect();
        cardNameView.setSelected(true);
        Card selectedCard = cardNameView.getCard();
        deckBuilderView.getDeckBuilder().select(selectedCard);
        ScrollView scrollList = (ScrollView) Utils.getContext().findViewById(R.id.scroll_list);
        scrollList.setBackgroundDrawable(getCardPicDrawable(selectedCard, scrollList.getWidth(), scrollList.getHeight()));
    }

    private Drawable getCardPicDrawable(Card card, int w, int h) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Bitmap cardBmp = card.getBmpGenerator().generate(CardSize.PREVIEW);
        canvas.drawBitmap(cardBmp, (w - CardSize.PREVIEW.width()) / 2, (h - CardSize.PREVIEW.height()) / 2, new Paint());
        canvas.drawARGB(60, 0, 0, 0);
        cardBmp.recycle();
        return new BitmapDrawable(bmp);
    }
}
