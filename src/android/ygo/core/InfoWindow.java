package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class InfoWindow implements Item {

    private static final Bitmap BACKGROUND = background();

    SelectableItem infoItem;

    public void setInfo(SelectableItem item) {
        infoItem = item;
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
        }
        return info;
    }

    public void clearInfo() {
        this.infoItem = null;
    }

    private static Bitmap background() {
        int width = Utils.totalWidth();
        int height = Utils.cardHeight() / 6 + 3;
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(background);
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(5);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(0, 0, 0, height, paint);
        canvas.drawLine(width, 0, width, height, paint);
        return background;
    }

    @Override
    public Bitmap toBitmap() {
        int width = Utils.totalWidth();
        int height = Utils.cardHeight() / 6 + 3;
        Bitmap winBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(winBmp);
        Paint paint = new Paint();

        Utils.drawBitmapOnCanvas(canvas, BACKGROUND, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);

        paint.setColor(Configuration.fontColor());
        paint.setStrokeWidth(1);
        paint.setTextSize(height);
        canvas.drawText(info(), 5, height - 2, paint);

        return winBmp;
    }
}
