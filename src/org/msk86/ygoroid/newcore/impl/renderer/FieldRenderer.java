package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.size.FieldSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.newutils.Style;

public class FieldRenderer implements Renderer {
    Field field;

    public FieldRenderer(Field field) {
        this.field = field;
    }

    @Override
    public Size size() {
        if (field.getType() == FieldType.MONSTER || field.getType() == FieldType.MAGIC_TRAP) {
            return FieldSize.SQUARE;
        } else {
            return FieldSize.RECT;
        }
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
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
        Item item = field.getItem();
        if (item == null) {
            return;
        }

        Layout layout = field.getLayout();
        Point p = layout.itemPosition(item);
        canvas.save();
        canvas.translate(x, y);
        item.getRenderer().draw(canvas, p.x, p.y);
        canvas.restore();
    }

}
