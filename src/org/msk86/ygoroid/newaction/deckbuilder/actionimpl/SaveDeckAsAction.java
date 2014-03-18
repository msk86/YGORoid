package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.DeckBuilderView;

public class SaveDeckAsAction implements Action {
    private DeckBuilderView view;
    private FrameLayout frameLayout;
    private EditText saveAsEdit;
    private AlertDialog saveAsDialog;

    public SaveDeckAsAction(DeckBuilderView view) {
        this.view = view;
    }

    @Override
    public void execute() {
        initSaveAsDialog();
        saveAsDialog.show();
    }

    private void initSaveAsDialog() {
        saveAsEdit = new EditText(Utils.getContext());
        saveAsEdit.setGravity(Gravity.CENTER);
        saveAsEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        saveAsEdit.setSingleLine();

        frameLayout = new FrameLayout(Utils.getContext());
        frameLayout.addView(saveAsEdit, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        saveAsDialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(Utils.s(R.string.DECK_NAME))
                .setPositiveButton(Utils.s(R.string.CONFIRM_SAVE), new OnSaveAsClickListener("OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnSaveAsClickListener("Cancel"))
                .create();
        saveAsDialog.setView(frameLayout);


        saveAsEdit.setOnEditorActionListener(new OnSaveAsEditorActionListener());
        saveAsEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    saveAsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }


    private class OnSaveAsClickListener implements DialogInterface.OnClickListener {

        private String button;

        public OnSaveAsClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                saveAs(saveAsEdit.getText().toString() + ".ydk");
            }
        }
    }

    private class OnSaveAsEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                saveAs(saveAsEdit.getText().toString() + ".ydk");
                saveAsDialog.hide();
            }

            return false;
        }
    }

    private void saveAs(String deckName) {
        boolean saved = view.getDeckBuilder().getCards().saveAs(deckName);
        DeckCards cards = view.getDeckBuilder().getCards();
        String info = String.format(Utils.s(R.string.SAVE_SUCCESS),
                cards.getDeckName(), cards.getMainDeckCards().size(), cards.getExDeckCards().size(), cards.getSideDeckCards().size());
        if (!saved) {
            info = String.format(Utils.s(R.string.SAVE_FAIL), cards.getDeckName());
        }
        Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
    }
}
