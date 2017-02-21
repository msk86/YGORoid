package org.msk86.ygoroid.newcore.constant;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.Bmpable;
import org.msk86.ygoroid.newcore.constant.bmp.FieldTypeGenerator;

public enum FieldType implements Bmpable {
    MONSTER, MAGIC_TRAP, FIELD_MAGIC, DECK, EX_DECK,
    GRAVEYARD, BANISHED, TEMP, EX_MONSTER;

    private int defaultBgResId;

    private FieldType() {
        this.defaultBgResId = -1;
    }

    private FieldType(int defaultBgResId) {
        this.defaultBgResId = defaultBgResId;
    }

    public int getDefaultBgResId() {
        return defaultBgResId;
    }

    private BmpGenerator generator = new FieldTypeGenerator(this);
    @Override
    public BmpGenerator getBmpGenerator() {
        return generator;
    }
}
