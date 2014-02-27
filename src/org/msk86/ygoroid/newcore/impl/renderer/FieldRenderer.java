package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.size.FieldSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Style;

public class FieldRenderer implements Renderer {
    Field field;
    int x, y;

    public FieldRenderer(Field field) {
        this.field = field;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public Size size() {
        if (field.getType() == FieldType.MONSTER_ZONE || field.getType() == FieldType.MAGIC_ZONE) {
            return FieldSize.SQUARE;
        } else {
            return FieldSize.RECT;
        }
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        this.x = x;
        this.y = y;
        drawFrame(canvas, x, y);
        drawItem(canvas, x, y);
    }

    private void drawFrame(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Style.fieldShadowColor());
        paint.setAlpha(80);

        canvas.save();

        canvas.translate(x, y);

        canvas.drawRect(new Rect(Style.padding(), Style.padding(), size().width() - Style.padding(), size().height() - Style.padding()), paint);

        paint.setColor(Style.fieldBorderColor());
        paint.setStrokeWidth(Style.border());
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(255);

        canvas.drawRect(new Rect(Style.padding(), Style.padding(), size().width() - Style.padding(), size().height() - Style.padding()), paint);

        canvas.restore();
    }

    private void drawItem(Canvas canvas, int x, int y) {
        if (field.getItem() == null) {
            return;
        }
        int offset = Style.padding() +  ((size().width() - Style.padding() * 2) - field.getItem().getRenderer().size().width()) / 2;

        Item item = field.getItem();

        canvas.save();
        canvas.translate(x, y);

        if(item instanceof Card) {
            Card card = (Card) item;
            if(card.isPositive()) {
                item.getRenderer().draw(canvas, offset, Style.padding());
            } else {
                item.getRenderer().draw(canvas, Style.padding(), offset);
            }
        } else if(item instanceof OverRay){
            item.getRenderer().draw(canvas, Style.padding(), Style.padding());
        } else {
            item.getRenderer().draw(canvas, offset, Style.padding());
        }
        canvas.restore();
    }

}