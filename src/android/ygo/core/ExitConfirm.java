package android.ygo.core;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.widget.FrameLayout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class ExitConfirm implements SelectableItem, Drawable {

    final FrameLayout layout;

    AlertDialog dialog;

    public ExitConfirm() {
    	layout = new FrameLayout(Utils.getContext());
    	createDialog();
    	dialog.show();
    }
    private void createDialog() {
        dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle("确定要退出程序吗？")
                .setPositiveButton("确定", new OnExitClickListener(this, "OK"))
                .setNegativeButton("取消", new OnExitClickListener(this, "Cancel"))
                .create();
        dialog.setView(layout);
    }

    @Override
    public String toString() {
        return "Exit";
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

    private static class OnExitClickListener implements DialogInterface.OnClickListener {
    	private ExitConfirm exitConfirm;
        private String button;

        public OnExitClickListener(ExitConfirm exitConfirm, String button) {
            this.exitConfirm = exitConfirm;
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
            	Utils.getContext().stopService();

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            exitConfirm.dialog.hide();
        }
    }

}
