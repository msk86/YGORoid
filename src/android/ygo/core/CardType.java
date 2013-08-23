package android.ygo.core;

import android.graphics.Bitmap;
import android.ygo.R;
import android.ygo.core.tool.BmpCache;
import android.ygo.utils.Utils;

public enum CardType implements Bmpable {
    NULL(0, "", R.raw.card_unknown),
    MONSTER(Const.TYPE_MONSTER, Utils.s(R.string.TYPE_MONSTER), 0),
    SPELL(Const.TYPE_SPELL, Utils.s(R.string.TYPE_SPELL), R.raw.card_spell),
    TRAP(Const.TYPE_TRAP, Utils.s(R.string.TYPE_TRAP), R.raw.card_trap);

    private int code;
    private String text;
    private int resId;

    BmpCache bmpCache;

    CardType(int code, String text, int resId) {
        this.code = code;
        this.text = text;
        this.resId = resId;
        bmpCache = new BmpCache(this);
    }

    public Bitmap bmp(int width, int height) {
        return Utils.readBitmapScaleByHeight(resId, height);
    }

    public void destroyBmp() {
        bmpCache.clear();
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
}
