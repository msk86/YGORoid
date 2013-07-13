package android.ygo.core;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LifePoint implements SelectableItem, Drawable {
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
        edit.setInputType(InputType.TYPE_CLASS_PHONE);
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
    public void draw(Canvas canvas, int x, int y) {
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(Utils.unitLength() / 4);
        textPaint.setColor(Configuration.fontColor());
        canvas.drawText(toString(), x + 8, y + Utils.unitLength() / 4, textPaint);
    }

    @Override
    public int width() {
        return (int) (Utils.unitLength() * 1.2);
    }

    @Override
    public int height() {
        return Utils.unitLength() / 4;
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
                try {
                    lifePoint.syncLP();
                } catch (Exception e) {
                }
            }
            lifePoint.dialog.hide();
            Utils.getContext().getDuelDiskView().updateActionTime();

            return false;
        }
    }

    private static final Pattern LP_PATTERN = Pattern.compile("^([\\+\\-=#\\*/\\.]?)(\\d+)$");

    private void syncLP() {
        String exp = edit.getText().toString();
        Matcher matcher = LP_PATTERN.matcher(exp);
        if (matcher.find()) {
            String op = matcher.group(1);
            int number = Integer.parseInt(matcher.group(2));
            if (op == null || op.length() == 0) {
                op = "=";
            }

            char opc = op.charAt(0);
            switch (opc) {
                case '+':
                    lp += number;
                    break;
                case '-':
                    lp -= number;
                    break;
                case '=':
                    lp = number;
                    break;
                case '*':
                    lp *= number;
                    break;
                case '/':
                case '#':
                    lp /= number;
                    break;
            }
        }
    }
}
