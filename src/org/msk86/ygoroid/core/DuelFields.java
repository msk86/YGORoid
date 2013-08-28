package org.msk86.ygoroid.core;

import android.graphics.Canvas;
import org.msk86.ygoroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DuelFields implements Item, Drawable {
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

        fieldMagicField = new Field(FieldType.FIELD_MAGIC_ZONE);
        fieldLine0.add(fieldMagicField);

        fieldLine0.add(null);
        fieldLine0.add(null);

        tempField = new Field(FieldType.TEMP);
        fieldLine0.add(tempField);

        removedField = new Field(FieldType.REMOVED);
        fieldLine0.add(removedField);

        graveyardField = new Field(FieldType.GRAVEYARD);
        fieldLine0.add(graveyardField);

        monsterZoneFields = new ArrayList<Field>();
        magicZoneFields = new ArrayList<Field>();

        for (int i = 0; i < 5; i++) {
            Field monsterField = new Field(FieldType.MONSTER_ZONE);
            monsterZoneFields.add(monsterField);
            fieldLine1.add(monsterField);
            Field magicField = new Field(FieldType.MAGIC_ZONE);
            magicZoneFields.add(magicField);
            fieldLine2.add(magicField);
        }
        exDeckField = new Field(FieldType.EXDECK);
        fieldLine1.add(exDeckField);

        deckField = new Field(FieldType.DECK);
        fieldLine2.add(deckField);
    }

    public Field fieldAt(int x, int y) {
        int fieldLength = Utils.unitLength();
        int indexX = x / fieldLength;
        int indexY = y / fieldLength;
        return fieldMatrix.get(indexY).get(indexX);
    }

    public SelectableItem itemOnFieldAt(int x, int y) {
        Field field = fieldAt(x, y);
        if (field != null) {
            return field.getItem();
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        int ul = Utils.unitLength();
        for (int dy = 0; dy < fieldMatrix.size(); dy++) {
            List<Field> fieldLine = fieldMatrix.get(dy);
            for (int dx = 0; dx < fieldLine.size(); dx++) {
                Field field = fieldLine.get(dx);
                if (field != null) {
                    helper.drawDrawable(canvas, field, ul * dx, ul * dy);
                }
            }
        }
    }

    @Override
    public int width() {
        return Utils.totalWidth();
    }

    @Override
    public int height() {
        return Utils.unitLength() * 3;
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
