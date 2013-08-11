package android.ygo.core.tool;

import android.ygo.core.Card;
import android.ygo.core.Const;

public class QuickFix {

    public static void fix(Card card) {
        // 格斯
        if("44330098".equals(card.getRealId())) {
            card.setCategory(Const.CATEGORY_TOKEN);
        }
        // 替罪羊
        if("73915051".equals(card.getRealId())) {
            card.setCategory(Const.CATEGORY_TOKEN);
        }
    }
}
