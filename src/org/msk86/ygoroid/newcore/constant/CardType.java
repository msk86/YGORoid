package org.msk86.ygoroid.newcore.constant;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.Bmpable;
import org.msk86.ygoroid.newcore.constant.bmp.CardTypeGenerator;
import org.msk86.ygoroid.utils.Utils2;

public enum CardType implements Bmpable {
    NULL(0, "", R.raw.card_unknown),
    MONSTER(Const.TYPE_MONSTER, Utils2.s(R.string.TYPE_MONSTER), 0),
    SPELL(Const.TYPE_SPELL, Utils2.s(R.string.TYPE_SPELL), R.raw.card_spell),
    TRAP(Const.TYPE_TRAP, Utils2.s(R.string.TYPE_TRAP), R.raw.card_trap);

    private int code;
    private String text;
    private int resId;

    CardType(int code, String text, int resId) {
        this.code = code;
        this.text = text;
        this.resId = resId;
    }

    public static CardType getCardType(int code) {
        for (CardType type : CardType.values()) {
            if (type == NULL) {
                continue;
            }
            if ((code & type.code) == type.code) {
                return type;
            }
        }
        return NULL;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getCode() {
        return code;
    }

    public int getResId() {
        return resId;
    }

    private BmpGenerator generator = new CardTypeGenerator(this);

    @Override
    public BmpGenerator getBmpGenerator() {
        return generator;
    }

    @Override
    public void destroyBmp() {
    }


}
