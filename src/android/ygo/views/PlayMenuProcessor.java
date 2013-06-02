package android.ygo.views;

import android.view.Menu;
import android.view.MenuItem;
import android.ygo.action.*;
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
        if(duel.getCardSelector() == null) {
            if(item instanceof Card) {
                menu.add(Const.MENU_GROUP_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0,"回卡组底");
                menu.add(Const.MENU_GROUP_CARD, Const.MENU_CARD_CLOSE_REMOVE, 0,"里侧除外");
            } else if (item instanceof Overlay) {

            } else if(item instanceof Deck) {
                if(((Deck)item).getName().equals("DECK")) {
                    menu.add(Const.MENU_GROUP_DECK,Const.MENU_DECK_SHUFFLE,0, "卡组洗切");
                    menu.add(Const.MENU_GROUP_DECK,Const.MENU_DECK_CLOSE_REMOVE_TOP,0, "卡组顶端里侧除外");
                    menu.add(Const.MENU_GROUP_DECK,Const.MENU_DECK_REVERSE,0,"卡组翻转");
                    menu.add(Const.MENU_GROUP_DECK,Const.MENU_DECK_RESTART,0,"重新开始");
                    menu.add(Const.MENU_GROUP_DECK,Const.MENU_DECK_CHANGE_DECK,0,"更换卡组");
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
        return true;
    }
}
