package org.msk86.ygoroid.newcore.impl.bmp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.impl.UserDefinedCard;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.TextUtils;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

import java.util.HashMap;
import java.util.Map;

public class UserDefinedCardGenerator implements BmpGenerator {
    private static Map<String, Bitmap> cache = new HashMap<String, Bitmap>();
    private UserDefinedCard card;

    public UserDefinedCardGenerator(UserDefinedCard card) {
        this.card = card;
    }

    private String cardBmpKey(Size size) {
        return card.getName() + size.toString();
    }

    @Override
    public Bitmap generate(Size size) {
        if (cache.get(cardBmpKey(size)) == null || cache.get(cardBmpKey(size)).isRecycled()) {
            cache.put(cardBmpKey(size), bmp(size));
        }

        return cache.get(cardBmpKey(size));
    }

    private Bitmap bmp(Size size) {
        Bitmap cardPic = BmpReader.readBitmap(card.getFileName(), size);
        if (cardPic == null) {
            cardPic = Bitmap.createBitmap(size.width(), size.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(cardPic);
            Paint paint = new Paint();

            Bitmap cardTypeBmp = card.getType().getBmpGenerator().generate(size);
            canvas.drawBitmap(cardTypeBmp, 0, 0, paint);

            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(cardNameFontSize(size));
            textPaint.setColor(Style.fontColor());
            textPaint.setShadowLayer(1, 0, 0, Style.textShadowColor());
            textPaint.setAntiAlias(true);
            CharSequence cs = TextUtils.cutOneLine(card.getName(), textPaint, size.width());
            StaticLayout layout = new StaticLayout(cs, textPaint, size.width(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);

            canvas.translate(0, size.height() / 20);
            layout.draw(canvas);
        }
        return cardPic;
    }

    private int cardNameFontSize(Size size) {
        if (size.height() <= CardSize.NORMAL.height()) {
            return size.height() / 10;
        } else {
            return size.height() / 15;
        }
    }

    public static void clearCache() {
        for (String key : cache.keySet()) {
            cache.get(key).recycle();
        }
        cache.clear();
    }
}
