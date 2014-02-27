package org.msk86.ygoroid.newcore.constant;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.utils.Utils2;

public enum Attribute {
    NULL(Const.NULL, ""),
    EARTH(Const.ATTRIBUTE_EARTH, Utils2.s(R.string.ATTRIBUTE_EARTH)),
    WATER(Const.ATTRIBUTE_WATER, Utils2.s(R.string.ATTRIBUTE_WATER)),
    FIRE(Const.ATTRIBUTE_FIRE, Utils2.s(R.string.ATTRIBUTE_FIRE)),
    WIND(Const.ATTRIBUTE_WIND, Utils2.s(R.string.ATTRIBUTE_WIND)),
    LIGHT(Const.ATTRIBUTE_LIGHT, Utils2.s(R.string.ATTRIBUTE_LIGHT)),
    DARK(Const.ATTRIBUTE_DARK, Utils2.s(R.string.ATTRIBUTE_DARK)),
    DIVINE(Const.ATTRIBUTE_DEVINE, Utils2.s(R.string.ATTRIBUTE_DEVINE));

    Attribute(int code, String text) {
        this.code = code;
        this.text = text;
    }

    private int code;
    private String text;

    public static Attribute getAttribute(int code) {
        for (Attribute attr : Attribute.values()) {
            if (attr.code == code) {
                return attr;
            }
        }
        return NULL;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return text;
    }
}
