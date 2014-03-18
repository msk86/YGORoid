package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.widget.Toast;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;

public class SaveDeckAction implements Action {
    private DeckBuilderView view;

    public SaveDeckAction(DeckBuilderView deckBuilderView) {
        this.view = deckBuilderView;

    }

    @Override
    public void execute() {
        DeckCards cards = view.getDeckBuilder().getCards();
        boolean saved = cards.save();
        String info = String.format(Utils.s(R.string.SAVE_SUCCESS),
                cards.getDeckName(), cards.getMainDeckCards().size(), cards.getExDeckCards().size(), cards.getSideDeckCards().size());
        if (!saved) {
            info = String.format(Utils.s(R.string.SAVE_FAIL), cards.getDeckName());
        }
        Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
    }
}
