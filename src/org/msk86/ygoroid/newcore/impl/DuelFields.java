package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newcore.impl.renderer.DuelFieldsRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuelFields implements Item, Container {
    Map<FieldType, Field> typeFieldMapping;
    Map<FieldType, List<Field>> typeFieldsMapping;

    public DuelFields() {
        typeFieldMapping = new HashMap<FieldType, Field>();
        typeFieldsMapping = new HashMap<FieldType, List<Field>>();

        for (FieldType type : new FieldType[]{FieldType.DECK, FieldType.EX_DECK, FieldType.GRAVEYARD,
                FieldType.BANISHED, FieldType.PENDULUM_LEFT, FieldType.PENDULUM_RIGHT, FieldType.TEMP,
                FieldType.FIELD_MAGIC}) {
            typeFieldMapping.put(type, new Field(type));
        }

        List<Field> monsterZone = new ArrayList<Field>();
        List<Field> magicZone = new ArrayList<Field>();
        for (int i = 0; i < 5; i++) {
            monsterZone.add(new Field(FieldType.MONSTER));
            magicZone.add(new Field(FieldType.MAGIC_TRAP));
        }
        typeFieldsMapping.put(FieldType.MONSTER, monsterZone);
        typeFieldsMapping.put(FieldType.MAGIC_TRAP, magicZone);
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
}
