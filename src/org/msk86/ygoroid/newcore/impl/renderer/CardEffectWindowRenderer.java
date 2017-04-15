package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.constant.CardType;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.TextUtils;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

public class CardEffectWindowRenderer implements Renderer {
    CardEffectWindow cardEffectWindow;

    public CardEffectWindowRenderer(CardEffectWindow cardEffectWindow) {
        this.cardEffectWindow = cardEffectWindow;
    }

    @Override
    public Size size() {
        return cardEffectWindow.getWindowHolder().getRenderer().size();
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBackground(canvas, x, y);
        drawCard(canvas, x, y);
        drawEffectText(canvas, x, y);
    }

    private void drawCard(Canvas canvas, int x, int y) {
        Bitmap cardBmp = cardEffectWindow.getCard().getBmpGenerator().generate(cardSize());

        canvas.save();
        canvas.translate(x, y);
        canvas.drawBitmap(cardBmp, 0, 0, new Paint());
        canvas.restore();
    }


    private void drawEffectText(Canvas canvas, int x, int y) {
        Card card = cardEffectWindow.getCard();

        canvas.save();
        canvas.translate(x, y);

        int fontSize = cardSize().height() / 25;
        Paint paint = new Paint();
        paint.setColor(Style.fontColor());
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);

        int top = fontSize * 3 / 2;
        int left = cardSize().width() + fontSize / 2;
        int lineHeight = fontSize * 3 / 2;

        canvas.drawText(card.getName(), left, top, paint);

        top += lineHeight;

        canvas.drawText(card.cardTypeDesc() + " " + card.attrAndRaceDesc(),
                left, top, paint);

        if (card.getType() == CardType.MONSTER) {
            top += lineHeight;

            canvas.drawText(card.levelAndADDesc() + " " + card.linkMarkerDirection(),
                    left, top, paint);
        }
        top += lineHeight;
        TextPaint textPaint = new TextPaint(paint);
        String pageText = TextUtils.cutPages(card.getDesc(), cardEffectWindow.getEffectTextPage(),
                textPaint, size().width() - cardSize().width() - fontSize / 2, size().height() - top);
        StaticLayout layout = new StaticLayout(pageText, textPaint,
                size().width() - cardSize().width() - fontSize / 2, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);

        canvas.translate(left, top);
        layout.draw(canvas);
    }


    private void drawBackground(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        Paint paint = new Paint();
        paint.setColor(Style.windowBackgroundColor());
        paint.setAlpha(150);
        canvas.drawRect(new Rect(0, 0, size().width(), size().height()), paint);
        canvas.restore();
    }

    private Size cardSize() {
        return new CardSize(cardEffectWindow.getWindowHolder().getRenderer().size().height());
    }
}
