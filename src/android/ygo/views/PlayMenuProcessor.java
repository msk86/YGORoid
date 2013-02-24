package android.ygo.views;

import android.view.Menu;
import android.view.MenuItem;
import android.ygo.action.Action;
import android.ygo.action.EmptyAction;
import android.ygo.action.ReserveDeckAction;
import android.ygo.action.ShuffleAction;
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
            } else if (item instanceof Overlay) {

            } else if(item instanceof Deck) {
                if(((Deck)item).getName().equals("DECK")) {
                    menu.add(Const.MENU_GROUP_DECK,Const.MENU_DECK_SHUFFLE,0, "卡组洗切");
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
        MenuClick menuClick = new MenuClick(duel);

        Action action = new EmptyAction();
        switch (menuItem.getGroupId()) {
            case Const.MENU_GROUP_DECK :
                switch (menuItem.getItemId()) {
                    case Const.MENU_DECK_SHUFFLE :
                        action = new ShuffleAction(menuClick);
                        break;
                    case Const.MENU_DECK_REVERSE :
                        action = new ReserveDeckAction(menuClick);
                        break;
                    case Const.MENU_DECK_RESTART :
                        break;
                    case Const.MENU_DECK_CHANGE_DECK :
                }
                break;
            case Const.MENU_GROUP_CARD :
                switch (menuItem.getItemId()) {
                    case Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK :
                }
                break;

        }
        action.execute();
        return true;
    }
}
