package org.msk86.ygoroid.newaction.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class SearchCardAction extends BaseAction {
    final FrameLayout layout;
    EditText edit;
    AlertDialog dialog;
    public SearchCardAction(Operation operation) {
        super(operation);
        layout = new FrameLayout(Utils.getContext());
        createEdit();
        createDialog();
    }

    @Override
    public void execute() {
        dialog.show();
    }

    private void createDialog() {
        layout.addView(edit, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        dialog = new AlertDialog.Builder(org.msk86.ygoroid.utils.Utils.getContext())
                .setTitle(org.msk86.ygoroid.utils.Utils.s(R.string.CARD_NAME))
                .setPositiveButton(org.msk86.ygoroid.utils.Utils.s(R.string.CONFIRM_YES), new OnCardSearchClickListener("OK"))
                .setNegativeButton(org.msk86.ygoroid.utils.Utils.s(R.string.CONFIRM_NO), new OnCardSearchClickListener("Cancel"))
                .create();
        dialog.setView(layout);
    }

    private void createEdit() {
        edit = new EditText(org.msk86.ygoroid.utils.Utils.getContext());
        edit.setGravity(Gravity.CENTER);
        edit.setSingleLine();
        edit.setOnEditorActionListener(new OnCardSearchActionListener());
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }

    private void search() {
        String text = edit.getText().toString();
        Card card = Utils.getDbHelper().loadByName(text);
        CardEffectWindow cardWindow = new CardEffectWindow(duel, card);
        duel.setCardEffectWindow(cardWindow);
        Utils.getContext().getDuelDiskView().updateActionTime();
    }

    private class OnCardSearchClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnCardSearchClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                search();
            }
            dialog.hide();
        }
    }

    private class OnCardSearchActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                search();
            }
            dialog.hide();
            return false;
        }
    }
}
