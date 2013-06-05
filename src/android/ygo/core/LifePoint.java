package android.ygo.core;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.InputType;
import android.text.TextPaint;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class LifePoint implements SelectableItem {
    int lp;

    EditText edit;

    final FrameLayout layout;

    AlertDialog dialog;

    public LifePoint() {
        this(8000);
    }

    public LifePoint(int lp) {
        this.lp = lp;
        layout = new FrameLayout(Utils.getContext());
        createEdit();
        createDialog();
    }

    private void createDialog() {
        layout.addView(edit, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle("请输入LP")
                .setPositiveButton("确定", new OnLPClickListener(this, "OK"))
                .setNegativeButton("取消", new OnLPClickListener(this, "Cancel"))
                .create();
        dialog.setView(layout);
    }

    private void createEdit() {
        edit = new EditText(Utils.getContext());
        edit.setGravity(Gravity.CENTER);
        edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        edit.setSingleLine();
        edit.setOnEditorActionListener(new OnLPEditorActionListener(this));
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }

    @Override
    public String toString() {
        return "LP: " + lp;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap lpBmp = Bitmap.createBitmap((int) (Utils.unitLength() * 1.2), Utils.unitLength() / 4, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(lpBmp);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(Utils.unitLength() / 4);
        textPaint.setColor(Configuration.fontColor());
        canvas.drawText(toString(), 8, Utils.unitLength() / 4, textPaint);
        return lpBmp;
    }

    @Override
    public void select() {
    }

    @Override
    public void unSelect() {
    }

    @Override
    public boolean isSelect() {
        return false;
    }

    public void showEditDialog() {
        edit.setText("");
        dialog.show();
    }

    private static class OnLPClickListener implements DialogInterface.OnClickListener {

        private LifePoint lifePoint;
        private String button;

        public OnLPClickListener(LifePoint lifePoint, String button) {
            this.lifePoint = lifePoint;
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                lifePoint.syncLP();
            }
            lifePoint.dialog.hide();
        }
    }

    private static class OnLPEditorActionListener implements TextView.OnEditorActionListener {

        LifePoint lifePoint;

        public OnLPEditorActionListener(LifePoint lifePoint) {
            this.lifePoint = lifePoint;
        }

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                lifePoint.syncLP();
            }
            lifePoint.dialog.hide();

            return false;
        }
    }

    private void syncLP() {
        try {
            lp = Integer.parseInt(edit.getText().toString());
        } catch (Exception e) {
        }
    }
}
