package android.ygo.core;

import java.util.List;

public class DeckChecker {

    public static final int MIN_MAIN_CARD_COUNT = 40;
    public static final int MAX_MAIN_CARD_COUNT = 60;
    public static final int MAX_EX_CARD_COUNT = 15;
    public static final int MAX_SIDE_CARD_COUNT = 15;
    public static final int MAX_SAME_CARD_COUNT = 3;


//    public static final String ERROR_MAIN = "主卡组卡片数量[%d]不符合要求";
//    public static final String ERROR_EX = "额外卡组卡片数量[%d]不符合要求";
//    public static final String ERROR_SIDE = "副卡组卡片数量[%d]不符合要求";

//    public static final String ERROR_CARD = "卡片%s数量不符合要求";

    public static boolean checkMainMax(List<Card> main, boolean oneMore) {
        return main.size() + (oneMore ? 1 : 0) <= MAX_MAIN_CARD_COUNT;
    }

    public static boolean checkMain(List<Card> main) {
        return main.size() >= MIN_MAIN_CARD_COUNT && main.size() <= MAX_MAIN_CARD_COUNT;
    }

    public static boolean checkEx(List<Card> ex) {
        return checkEx(ex, false);
    }

    public static boolean checkEx(List<Card> ex, boolean oneMore) {
        return ex.size() + (oneMore ? 1 : 0) <= MAX_EX_CARD_COUNT;
    }

    public static boolean checkSide(List<Card> side) {
        return side.size() <= MAX_SIDE_CARD_COUNT;
    }

    public static boolean checkSide(List<Card> side, boolean oneMore) {
        return side.size() + (oneMore ? 1 : 0) <= MAX_SIDE_CARD_COUNT;
    }

    public static boolean checkSingleCard(List<Card> allCards, Card card) {
        return checkSingleCard(allCards, card, false);
    }

    public static boolean checkSingleCard(List<Card> allCards, Card card, boolean oneMore) {
        int count = 0;

        for (Card existCard : allCards) {
            if (!(existCard instanceof UserDefinedCard || existCard.getRealId().equals("0"))) {
                if (existCard.getRealId().equals(card.getRealId())) {
                    count++;
                }
            } else {
                if (existCard.getName().equals(card.getName())) {
                    count++;
                }
            }
        }

        return count + (oneMore ? 1 : 0) <= MAX_SAME_CARD_COUNT;
    }
}
