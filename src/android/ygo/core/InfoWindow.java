package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class InfoWindow implements Item {

    SelectableItem infoItem;

    public void setInfo(SelectableItem item) {
        infoItem = item;
    }

    private String info() {
        String info = "";
        if(infoItem != null) {
            if (infoItem instanceof Card) {
                info = infoItem.toString();
            } else if (infoItem instanceof Overlay) {
                info = ((Overlay) infoItem).topCard().toString();
            } else if (infoItem instanceof CardList) {
                CardList cardList = (CardList) infoItem;
                info = cardList.getName() + "[" + cardList.size() + "]";
                if(cardList.isOpen() && cardList.size() > 0) {
                    info += " / " + cardList.topCard().toString();
                }
            }
        }
        return info;
    }

    public void clearInfo() {
        this.infoItem = null;
    }

    @Override
    public Bitmap toBitmap() {
        int width = Utils.totalWidth();
        int height = Utils.cardHeight() / 6 + 3;
        Bitmap winBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(winBmp);
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(5);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(0, 0, 0, height, paint);
        canvas.drawLine(width, 0, width, height, paint);


        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        paint.setTextSize(height);
        canvas.drawText(info(), 5, height - 2, paint);

        return winBmp;
    }
}
