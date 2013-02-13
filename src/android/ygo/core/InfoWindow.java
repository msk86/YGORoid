package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class InfoWindow implements Item {

    String info = "";

    public void setInfo(SelectableItem item) {
        String info = "";
        if (item instanceof Card) {
            info = ((Card) item).toString();
        } else if (item instanceof Overlay) {
            info = ((Overlay) item).topCard().toString();
        } else if (item instanceof CardList) {
            CardList cardList = (CardList) item;
            info = cardList.getName() + "[" + cardList.size() + "]";
        }
        setInfo(info);
    }

    public void setInfo(String info) {
        this.info = info == null ? "" : info;
    }

    public void clearInfo() {
        this.info = "";
    }

    @Override
    public Bitmap toBitmap() {
        int width = Utils.unitLength() * 6;
        int height = Utils.cardHeight() / 6 + 3;
        Bitmap winBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(winBmp);
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(5);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(0, 0, 0, height, paint);
        canvas.drawLine(width, 0, width, height, paint);


        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        paint.setTextSize(height);
        canvas.drawText(info, 5, height - 2, paint);

        return winBmp;
    }
}
