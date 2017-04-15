package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.bmp.DuelFieldsGenerator;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newcore.impl.renderer.DuelFieldsRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuelFields implements Item, Container, Bmpable {
    Map<FieldType, Field> typeFieldMapping;
    Map<FieldType, List<Field>> typeFieldsMapping;

    public DuelFields() {
        typeFieldMapping = new HashMap<FieldType, Field>();
        typeFieldsMapping = new HashMap<FieldType, List<Field>>();

        for (FieldType type : new FieldType[]{FieldType.DECK, FieldType.EX_DECK, FieldType.GRAVEYARD,
                FieldType.BANISHED, FieldType.EX_MONSTER, FieldType.TEMP,
                FieldType.FIELD_MAGIC}) {
            typeFieldMapping.put(type, new Field(type, this));
        }

        List<Field> monsterZone = new ArrayList<Field>();
        List<Field> magicZone = new ArrayList<Field>();
        List<Field> exMonsterZone = new ArrayList<Field>();
        for (int i = 0; i < 5; i++) {
            monsterZone.add(new Field(FieldType.MONSTER, this));
            magicZone.add(new Field(FieldType.MAGIC_TRAP, this));
        }
        for (int i = 0; i < 2; i++) {
            exMonsterZone.add(new Field(FieldType.EX_MONSTER, this));
        }
        typeFieldsMapping.put(FieldType.MONSTER, monsterZone);
        typeFieldsMapping.put(FieldType.MAGIC_TRAP, magicZone);
        typeFieldsMapping.put(FieldType.EX_MONSTER, exMonsterZone);
    }

    public Field getField(FieldType type) {
        return typeFieldMapping.get(type);
    }

    public List<Field> getFields(FieldType type) {
        return typeFieldsMapping.get(type);
    }

    Renderer renderer;

    @Override
    public Renderer getRenderer() {
        if (renderer == null) {
            renderer = new DuelFieldsRenderer(this);
        }
        return renderer;
    }

    Layout layout;

    @Override
    public Layout getLayout() {
        if (layout == null) {
            layout = new AbsoluteLayout(this);
        }
        return layout;
    }

    BmpGenerator generator;
    @Override
    public BmpGenerator getBmpGenerator() {
        if (generator == null) {
            generator = new DuelFieldsGenerator(this);
        }
        return generator;
    }
}
