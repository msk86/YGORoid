package android.ygo.core;

import android.graphics.Bitmap;
import android.ygo.utils.Utils;

public enum CardType {
    NULL(0, ""),
    MONSTER(Const.TYPE_MONSTER, "怪兽"),
    SPELL(Const.TYPE_SPELL, "魔法"),
    TRAP(Const.TYPE_TRAP, "陷阱")
    ;

    private int code;
    private String text;
    private Bitmap cardBmp;

    CardType(int code, String text) {
        this.code = code;
        this.text = text;
        try {
            cardBmp = Utils.readBitmapScaleByHeight("textures/" + "card-" + code + ".jpg", Utils.cardHeight());
        } catch (Exception e) {
        }
    }

    public static CardType getCardType(int code) {
        for (CardType type : CardType.values()) {
            if(type == NULL) {
                continue;
            }
            if ((code & type.code) == type.code) {
                return type;
            }
        }
        return NULL;
    }

    public Bitmap getCardBmp() {
        return cardBmp;
    }

    @Override
    public String toString() {
        return text;
    }
}
