package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.DuelFields;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.newutils.Configuration;
import org.msk86.ygoroid.size.FieldSize;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;

public class DuelFieldsRenderer implements Renderer {
    DuelFields fields;

    public DuelFieldsRenderer(DuelFields fields) {
        this.fields = fields;

        AbsoluteLayout layout = (AbsoluteLayout) fields.getLayout();

        layout.addItem(fields.getField(FieldType.TEMP), 0, 0);
        layout.addItem(fields.getField(FieldType.FIELD_MAGIC), 0, FieldSize.SQUARE.height());
        layout.addItem(fields.getField(FieldType.PENDULUM_LEFT), 0, FieldSize.SQUARE.height() * 2);
        layout.addItem(fields.getField(FieldType.EX_DECK), 0, FieldSize.SQUARE.height() * 3);

        layout.addItem(fields.getField(FieldType.BANISHED), FieldSize.RECT.width() + FieldSize.SQUARE.width() * 5, 0);
        layout.addItem(fields.getField(FieldType.GRAVEYARD), FieldSize.RECT.width() + FieldSize.SQUARE.width() * 5, FieldSize.SQUARE.height());
        layout.addItem(fields.getField(FieldType.PENDULUM_RIGHT), FieldSize.RECT.width() + FieldSize.SQUARE.width() * 5, FieldSize.SQUARE.height() * 2);
        layout.addItem(fields.getField(FieldType.DECK), FieldSize.RECT.width() + FieldSize.SQUARE.width() * 5, FieldSize.SQUARE.height() * 3);

        for (int i = 0; i < 5; i++) {
            layout.addItem(fields.getFields(FieldType.MONSTER).get(i), FieldSize.RECT.width() + FieldSize.SQUARE.width() * i, FieldSize.SQUARE.height());
            layout.addItem(fields.getFields(FieldType.MAGIC_TRAP).get(i), FieldSize.RECT.width() + FieldSize.SQUARE.width() * i, FieldSize.SQUARE.height() * 2);
        }
    }

    @Override
    public Size size() {
        return OtherSize.DUEL_FIELDS;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBmpFrame(canvas, x, y);
        drawItems(canvas, x, y);
    }

    private void drawItems(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        Layout layout = fields.getLayout();
        for (Item item : layout.items()) {
            Point point = layout.itemPosition(item);
            item.getRenderer().draw(canvas, point.x, point.y);
        }
    }

    private void drawBmpFrame(Canvas canvas, int x, int y) {
        Bitmap bmp = fields.getBmpGenerator().generate(size());
        if(bmp != null) {
            hasBmpFrame = true;
            canvas.drawBitmap(bmp, x, y, new Paint());
        }
    }

    private boolean hasBmpFrame = false;
    public boolean hasBmpFrame() {
        return hasBmpFrame;
    }
}
