package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;

public class DeleteDeckAction implements Action {
    DeckBuilderView deckBuilderView;

    public DeleteDeckAction(DeckBuilderView deckBuilderView) {
        this.deckBuilderView = deckBuilderView;
    }

    @Override
    public void execute() {
        DeckCards cards = deckBuilderView.getDeckBuilder().getCards();
        if(cards.getAllCards().size() == 0) {
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(String.format(Utils.s(R.string.DELETE_DECK), cards.getDeckName()))
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnDeleteClickListener("OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnDeleteClickListener("Cancel"))
                .create();
        dialog.show();
    }

    private class OnDeleteClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnDeleteClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                DeckCards cards = deckBuilderView.getDeckBuilder().getCards();
                cards.delete();
                deckBuilderView.getDeckBuilder().recycleUselessBmp();
                String info = String.format(Utils.s(R.string.DELETE_SUCCESS), cards.getDeckName());
                Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
            }
        }
    }
}
