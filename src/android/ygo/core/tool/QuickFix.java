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
        // 魂之纲
        if("37383714".equals(card.getRealId())) {
            card.setDesc("自己场上的怪兽被卡的效果破坏送去墓地时，支付1000基本分才能发动。从卡组把1只4星怪兽特殊召唤。");
        }
        // 地狱女帝恶魔
        if("31766317".equals(card.getRealId())) {
            card.setDesc("这张卡以外的场上表侧表示存在的恶魔族·暗属性怪兽1只被破坏的场合，可以作为代替把自己墓地存在的1只恶魔族·暗属性怪兽从游戏中除外。此外，场上存在的这张卡被破坏送去墓地时，可以选择「地狱女帝恶魔」以外的自己墓地存在的1只恶魔族·暗属性·6星以上的怪兽特殊召唤。");
        }
    }

}
