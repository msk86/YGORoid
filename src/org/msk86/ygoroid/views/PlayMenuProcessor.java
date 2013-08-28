package org.msk86.ygoroid.views;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.ygo.R;
import org.msk86.ygoroid.action.Action;
import org.msk86.ygoroid.action.ActionDispatcher;
import org.msk86.ygoroid.op.MenuClick;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;
import org.msk86.ygoroid.views.dueldisk.DuelDiskView;
import org.msk86.ygoroid.core.*;

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

        if (duel.isDuelDisk()) {
            if (container instanceof Field) {
                if (item instanceof OverRay || item instanceof Card) {
                    Card card;
                    if (item instanceof OverRay) {
                        card = ((OverRay) item).topCard();
                    } else {
                        card = (Card) item;
                    }
                    if (!card.isToken()) {
                        menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, Utils.s(R.string.MENU_CARD_BACK_TO_BOTTOM_OF_DECK));
                        menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_CLOSE_REMOVE, 0, Utils.s(R.string.MENU_CARD_CLOSE_REMOVE));
                    }
                } else if (item instanceof CardList) {
                    CardList cardList = (CardList) item;
                    if (cardList.getName().equals(Utils.s(R.string.DECK))) {
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SHUFFLE, 0, Utils.s(R.string.MENU_DECK_SHUFFLE));
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_CLOSE_REMOVE_TOP, 0, Utils.s(R.string.MENU_DECK_CLOSE_REMOVE_TOP));
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_REVERSE, 0, Utils.s(R.string.MENU_DECK_REVERSE));
                    } else if (cardList.getName().equals(Utils.s(R.string.TEMPORARY))) {
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SHUFFLE, 0, Utils.s(R.string.MENU_DECK_SHUFFLE));
                    }
                }
            } else if (container instanceof HandCards) {
                if (item instanceof Card) {
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, Utils.s(R.string.MENU_CARD_BACK_TO_BOTTOM_OF_DECK));
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_CLOSE_REMOVE, 0, Utils.s(R.string.MENU_CARD_CLOSE_REMOVE));
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_SHOW_HAND, 0, Utils.s(R.string.MENU_SHOW_HAND));
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_HIDE_HAND, 0, Utils.s(R.string.MENU_HIDE_HAND));
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_SHUFFLE_HAND, 0, Utils.s(R.string.MENU_SHUFFLE_HAND));
                }
            } else {
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_RESTART, 0, Utils.s(R.string.MENU_RESTART));
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_CHANGE_DECK, 0, Utils.s(R.string.MENU_CHANGE_DECK));
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_SIDE, 0, Utils.s(R.string.MENU_SIDE));
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_CARD_SEARCH, 0, Utils.s(R.string.MENU_CARD_SEARCH));
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_DECK_BUILDER, 0, Utils.s(R.string.MENU_DECK_BUILDER));
                SubMenu toggleMenu = menu.addSubMenu(Const.MENU_GROUP_MAIN, Const.MENU_TOGGLE, 0, Utils.s(R.string.MENU_TOGGLE));
                toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_GRAVITY_TOGGLE, 0, toggleMenuTxt(Utils.s(R.string.MENU_GRAVITY_TOGGLE), Configuration.PROPERTY_GRAVITY_ENABLE));
                toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_AUTO_SHUFFLE_TOGGLE, 0, toggleMenuTxt(Utils.s(R.string.MENU_AUTO_SHUFFLE_TOGGLE), Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE));
                toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_FPS_TOGGLE, 0, toggleMenuTxt(Utils.s(R.string.MENU_FPS_TOGGLE), Configuration.PROPERTY_FPS_ENABLE));
                if (Utils.getSDK() >= 10) {
                    menu.add(Const.MENU_GROUP_MAIN, Const.MENU_MIRROR_DISPLAY, 0, Utils.s(R.string.MENU_MIRROR_DISPLAY));
                }
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_FEEDBACK, 0, Utils.s(R.string.MENU_FEEDBACK));
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_EXIT, 0, Utils.s(R.string.MENU_EXIT));
            }
        }
        return true;
    }

    private String toggleMenuTxt(String txt, String toggle) {
        boolean toggleTxt = Configuration.configProperties(toggle);
        return txt + "(" + (toggleTxt ? Utils.s(R.string.MENU_TOGGLE_ON) : Utils.s(R.string.MENU_TOGGLE_OFF)) + ")";
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
