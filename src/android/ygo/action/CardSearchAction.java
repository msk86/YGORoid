package android.ygo.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.ygo.core.Card;
import android.ygo.core.ShowCardWindow;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class CardSearchAction extends BaseAction {

    EditText edit;

    final FrameLayout layout;

    AlertDialog dialog;

    public CardSearchAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());

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

        dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle("请输入卡名")
                .setPositiveButton("确定", new OnCardSearchClickListener("OK"))
                .setNegativeButton("取消", new OnCardSearchClickListener("Cancel"))
                .create();
        dialog.setView(layout);
    }

    private void createEdit() {
        edit = new EditText(Utils.getContext());
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
        ShowCardWindow cardWindow = new ShowCardWindow(card);
        duel.setCardWindow(cardWindow);
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
