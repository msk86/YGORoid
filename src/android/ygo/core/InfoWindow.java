package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class InfoWindow implements Item {

    Card card;

    public void setCard(Card card) {
        this.card = card;
    }

    private String cardDesc() {
        if (card == null) {
            return "";
        }
        return "流天类星龙 L12 4000/4000 光 龙";
    }

    @Override
    public Bitmap toBitmap() {
        int width = Utils.unitLength() * 4;
        int height = Utils.cardHeight() / 7 + 3;
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
        canvas.drawText(cardDesc(), 5, height - 2, paint);

        return winBmp;
    }
}
