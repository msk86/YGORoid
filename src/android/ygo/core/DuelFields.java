package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DuelFields implements Item {
    List<Field> monsterZoneFields;
    List<Field> magicZoneFields;
    Field deckField;
    Field graveyardField;
    Field removedField;
    Field exDeckField;
    Field tempField;
    Field fieldMagicField;

    public DuelFields() {
        monsterZoneFields = new ArrayList<Field>();
        magicZoneFields = new ArrayList<Field>();

        for(int i=0;i<5;i++) {
            Field monsterField = new Field();
            monsterZoneFields.add(monsterField);
            Field magicField = new Field();
            magicZoneFields.add(magicField);
        }
        deckField = new DeckField();
        graveyardField = new DeckField();
        removedField = new DeckField();
        exDeckField = new DeckField();
        fieldMagicField = new FieldMagicField();
        tempField = new DeckField();
    }

    @Override
    public Bitmap toBitmap() {
        int width = Utils.unitLength() * 6;
        int height = Utils.unitLength() * 3;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        int ul = Utils.unitLength();

        int[] xs = {0, ul, ul * 2, ul * 3, ul * 4, ul * 5};
        int[] ys = {0, ul, ul * 2, ul * 3};

        canvas.drawBitmap(fieldMagicField.toBitmap(), xs[0], ys[0], paint);
        canvas.drawBitmap(graveyardField.toBitmap(), xs[3], ys[0], paint);
        canvas.drawBitmap(removedField.toBitmap(), xs[4], ys[0], paint);
        canvas.drawBitmap(tempField.toBitmap(), xs[5], ys[0], paint);

        for(int i = 0; i < monsterZoneFields.size(); i++) {
            Field field  = monsterZoneFields.get(i);
            canvas.drawBitmap(field.toBitmap(), xs[i], ys[1], paint);
        }
        canvas.drawBitmap(exDeckField.toBitmap(), xs[5], ys[1], paint);

        for(int i = 0; i < magicZoneFields.size(); i++) {
            Field field  = magicZoneFields.get(i);
            canvas.drawBitmap(field.toBitmap(), xs[i], ys[2], paint);
        }
        canvas.drawBitmap(deckField.toBitmap(), xs[5], ys[2], paint);

        return bitmap;
    }

    //test
    public Field testField() {
        return monsterZoneFields.get(2);
    }
}
