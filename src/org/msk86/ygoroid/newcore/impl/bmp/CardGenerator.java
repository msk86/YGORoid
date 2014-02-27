package org.msk86.ygoroid.newcore.impl.bmp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.constant.CardSubType;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.BmpReader;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils2;

import java.util.HashMap;
import java.util.Map;

public class CardGenerator implements BmpGenerator {
    private Card card;
    private Map<Size, Bitmap> cache = new HashMap<Size, Bitmap>();

    public CardGenerator(Card card) {
        this.card = card;
    }

    @Override
    public Bitmap generate(Size size) {
        if(cache.get(size) == null) {
            cache.put(size, bmp(size));
        }

        return cache.get(size);
    }

    private Bitmap bmp(Size size) {
        Bitmap cardPic = BmpReader.readBitmap(Configuration.cardImgPath() + card.getId() + Configuration.cardImageSuffix(), size);
        if (cardPic == null) {
            cardPic = Bitmap.createBitmap(size.width(), size.height(), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(cardPic);
            Paint paint = new Paint();

            Bitmap cardTypeBmp = cardTypeBmp(size);
            Utils2.drawBitmapOnCanvas(canvas, cardTypeBmp, paint, Utils2.DRAW_POSITION_FIRST, Utils2.DRAW_POSITION_FIRST);

            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(cardNameFontSize(size));

            if (!card.getSubTypes().contains(CardSubType.SYNC)) {
                textPaint.setColor(Configuration.fontColor());
                textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());
            } else {
                textPaint.setColor(Configuration.syncFontColor());
            }
            textPaint.setAntiAlias(true);
            CharSequence cs = Utils2.cutOneLine(card.getName(), textPaint, size.width());
            StaticLayout layout = new StaticLayout(cs, textPaint, size.width(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);

            canvas.translate(0, size.height() / 20);
            layout.draw(canvas);
        }
        return cardPic;
    }

    private Bitmap cardTypeBmp(Size size) {
        Bitmap typeBmp = card.getType().getBmpGenerator().generate(size);
        if (typeBmp != null) {
            return typeBmp;
        }

        for (int i = card.getSubTypes().size() - 1; i >= 0; i--) {
            CardSubType subType = card.getSubTypes().get(i);
            Bitmap subTypeBmp = subType.getBmpGenerator().generate(size);
            if (subTypeBmp != null) {
                return subTypeBmp;
            }
        }

        return null;
    }

    private int cardNameFontSize(Size size) {
        if(size == CardSize.SNAPSHOT || size == CardSize.NORMAL) {
            return size.height() / 10;
        } else {
            return size.height() / 15;
        }
    }
}