package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;

public class ChangeDeckAction implements Action {
    DeckBuilderView view;

    public ChangeDeckAction(DeckBuilderView view) {
        this.view = view;
    }

    @Override
    public void execute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
        builder.setTitle(Utils.s(R.string.CHOOSE_DECK));
        String[] decks = Utils.decks();
        final String[] deckList = new String[decks.length + 1];
        deckList[0] = Utils.s(R.string.NEW_DECK);
        System.arraycopy(decks, 0, deckList, 1, decks.length);
        builder.setItems(deckList, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                view.getDeckBuilder().recycleUselessBmp();
                if (which == 0) {
                    newDeck();
                } else {
                    String deck = deckList[which];
                    loadDeck(deck);
                }

                view.updateActionTime();
                clearQueryText();
            }
        });
        builder.create().show();
    }

    private void clearQueryText() {
        EditText searchTextView = (EditText) Utils.getContext().findViewById(R.id.search_text);
        searchTextView.setText("");
        view.getSearchResultList().clear();
    }

    private void newDeck() {
        view.getDeckBuilder().newDeck();
    }

    private void loadDeck(String deckName) {
        view.getDeckBuilder().loadDeck(deckName);
    }
}
