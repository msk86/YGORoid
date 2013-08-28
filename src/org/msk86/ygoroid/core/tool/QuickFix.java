package org.msk86.ygoroid.core.tool;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Const;

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
        // 替罪羊
        if("73915051".equals(card.getRealId())) {
            card.setCategory(Const.CATEGORY_TOKEN);
        }
    }

}
