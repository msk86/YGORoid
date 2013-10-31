package org.msk86.ygoroid.core;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.utils.Utils;

public enum Attribute {
    NULL(Const.NULL, ""),
    EARTH(Const.ATTRIBUTE_EARTH, Utils.s(R.string.ATTRIBUTE_EARTH)),
    WATER(Const.ATTRIBUTE_WATER, Utils.s(R.string.ATTRIBUTE_WATER)),
    FIRE(Const.ATTRIBUTE_FIRE, Utils.s(R.string.ATTRIBUTE_FIRE)),
    WIND(Const.ATTRIBUTE_WIND, Utils.s(R.string.ATTRIBUTE_WIND)),
    LIGHT(Const.ATTRIBUTE_LIGHT, Utils.s(R.string.ATTRIBUTE_LIGHT)),
    DARK(Const.ATTRIBUTE_DARK, Utils.s(R.string.ATTRIBUTE_DARK)),
    DIVINE(Const.ATTRIBUTE_DEVINE, Utils.s(R.string.ATTRIBUTE_DEVINE));

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
