package org.msk86.ygoroid.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.utils.Utils;
import org.msk86.ygoroid.views.deckbuilder.DeckBuilderView;

public class DeckOnKeyProcessor {
    DeckBuilderView view;

    public DeckOnKeyProcessor(DeckBuilderView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        if (view.getCardWindow() == null) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                            .setTitle(Utils.s(R.string.QUIT_DECK_BUILDER))
                            .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnExitClickListener("OK"))
                            .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnExitClickListener("Cancel"))
                            .create();
                    dialog.show();
                    break;
                case KeyEvent.KEYCODE_VOLUME_UP :
                    view.sortAllCards();
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN :
                    view.shuffleAllCards();
                    break;
            }
        } else {
            view.setCardWindow(null);
            view.updateActionTime();
        }
        return true;
    }

    private class OnExitClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnExitClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                Utils.getContext().showDuelWithDeck(view.getDeckBuilder().getOrgDeckName());
            }
        }
    }
}
