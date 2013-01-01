package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DuelFields implements Item {
    private List<List<Field>> fieldMatrix;

    List<Field> monsterZoneFields;
    List<Field> magicZoneFields;
    Field deckField;
    Field graveyardField;
    Field removedField;
    Field exDeckField;
    Field tempField;
    Field fieldMagicField;

    public DuelFields() {
        fieldMatrix = new ArrayList<List<Field>>();

        List<Field> fieldLine0 = new ArrayList<Field>();
        List<Field> fieldLine1 = new ArrayList<Field>();
        List<Field> fieldLine2 = new ArrayList<Field>();
        fieldMatrix.add(fieldLine0);
        fieldMatrix.add(fieldLine1);
        fieldMatrix.add(fieldLine2);

        fieldMagicField = new FieldMagicField();
        fieldLine0.add(fieldMagicField);

        fieldLine0.add(null);
        fieldLine0.add(null);

        graveyardField = new Field();
        fieldLine0.add(graveyardField);

        removedField = new Field();
        fieldLine0.add(removedField);

        tempField = new Field();
        fieldLine0.add(tempField);

        monsterZoneFields = new ArrayList<Field>();
        magicZoneFields = new ArrayList<Field>();

        for (int i = 0; i < 5; i++) {
            Field monsterField = new Field();
            monsterZoneFields.add(monsterField);
            fieldLine1.add(monsterField);
            Field magicField = new Field();
            magicZoneFields.add(magicField);
            fieldLine2.add(magicField);
        }
        exDeckField = new Field();
        fieldLine1.add(exDeckField);

        deckField = new Field();
        fieldLine2.add(deckField);
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

        for(int y=0;y<fieldMatrix.size();y++) {
            List<Field> fieldLine = fieldMatrix.get(y);
            for(int x=0;x<fieldLine.size();x++) {
                Field field = fieldLine.get(x);
                if(field != null) {
                    Utils.drawBitmapOnCanvas(canvas, field.toBitmap(), paint, ul*x, ul *y);
                }
            }
        }

        return bitmap;
    }

    public Field getMonsterField(int i) {
        return monsterZoneFields.get(i);
    }

    public Field getMagicField(int i) {
        return magicZoneFields.get(i);
    }

    public Field getDeckField() {
        return deckField;
    }

    public Field getExDeckField() {
        return exDeckField;
    }

    public Field getGraveyardField() {
        return graveyardField;
    }

    public Field getRemovedField() {
        return removedField;
    }

    public Field getTempField() {
        return tempField;
    }
}
