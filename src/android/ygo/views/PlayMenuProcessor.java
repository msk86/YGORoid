package android.ygo.views;

import android.view.Menu;
import android.view.MenuItem;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.core.*;
import android.ygo.op.MenuClick;

public class PlayMenuProcessor {

    DuelDiskView view;

    public PlayMenuProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onMenuPrepare(Menu menu) {
        menu.clear();
        Duel duel = view.getDuel();
        SelectableItem item = duel.getCurrentSelectItem();
        Item container = duel.getCurrentSelectItemContainer();
        if (duel.getCardSelector() == null) {
            if (container instanceof Field) {
                if (item instanceof Card || item instanceof OverRay) {
                    menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, "回卡组底");
                    menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_CLOSE_REMOVE, 0, "里侧除外");
                } else if (item instanceof Deck) {
                    if (((Deck) item).getName().equals(CardList.DECK)) {
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SHUFFLE, 0, "卡组洗切");
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_CLOSE_REMOVE_TOP, 0, "卡组顶端里侧除外");
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_REVERSE, 0, "卡组翻转");
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_RESTART, 0, "重新开始");
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_CHANGE_DECK, 0, "更换卡组");
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SIDE, 0, "换副卡组");
                        if(android.os.Build.VERSION.SDK_INT >= 10) {
                            menu.add(Const.MENU_GROUP_DECK, Const.MENU_MIRROR_DISPLAY, 0, "镜像显示");
                        }
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_EXIT, 0, "退出");
                    }
                }
            } else if (container instanceof HandCards) {
                if (item instanceof Card) {
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, "回卡组底");
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_CLOSE_REMOVE, 0, "里侧除外");
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_SHOW_HAND, 0, "展示手牌");
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_HIDE_HAND, 0, "覆盖手牌");
                }
            }
        }
        return true;
    }

    public boolean onMenuClick(MenuItem menuItem) {
        Duel duel = view.getDuel();
        MenuClick menuClick = new MenuClick(duel, menuItem);
        Action action = ActionDispatcher.dispatch(menuClick);
        action.execute();
        view.updateActionTime();
        return true;
    }
}
