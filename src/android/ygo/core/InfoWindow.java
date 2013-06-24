package android.ygo.core;

import android.graphics.*;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class InfoWindow implements SelectableItem, Drawable {
    SelectableItem infoItem;
    private String info;

    public void setInfo(SelectableItem item) {
        infoItem = item;
        info = null;
    }

    public void setInfo(String info) {
        infoItem = null;
        this.info = info;
    }

    private String info() {
        String info = "";
        if (infoItem != null) {
            if (infoItem instanceof Card) {
                info = infoItem.toString();
            } else if (infoItem instanceof OverRay) {
                info = ((OverRay) infoItem).topCard().toString();
            } else if (infoItem instanceof CardList) {
                CardList cardList = (CardList) infoItem;
                info = cardList.getName() + "[" + cardList.size() + "]";
                if (cardList.size() > 0 && cardList.topCard().isOpen()) {
                    info += " / " + cardList.topCard().toString();
                }
            }
        } else if(this.info != null) {
            info = this.info;
        }
        return info;
    }

    public void clearInfo() {
        this.infoItem = null;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawFrame(canvas, x, y);

        Paint paint = new Paint();

        paint.setColor(Configuration.fontColor());
        paint.setStrokeWidth(1);
        paint.setTextSize(height());
        paint.setAntiAlias(true);

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        helper.drawText(canvas, info(), 5, height() - 2, paint);
    }

    private void drawFrame(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        helper.drawRect(canvas, new Rect(0, 0, width(), height()), paint);

        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(5);
        helper.drawLine(canvas, 0, 0, width(), 0, paint);
        helper.drawLine(canvas, 0, 0, 0, height(), paint);
        helper.drawLine(canvas, width(), 0, width(), height(), paint);
    }

    @Override
    public int width() {
        return Utils.totalWidth();
    }

    @Override
    public int height() {
        return Utils.cardHeight() / 6 + 3;
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

    public SelectableItem getInfoItem() {
        return infoItem;
    }
}
