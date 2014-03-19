package org.msk86.ygoroid.views.newdueldisk;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl.MenuClickDispatcher;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.constant.Const;
import org.msk86.ygoroid.newcore.impl.*;
import org.msk86.ygoroid.newop.impl.MenuClick;
import org.msk86.ygoroid.newutils.LayoutUtils;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.views.OnMenuProcessor;

import java.util.List;

public class PlayOnMenuProcessor implements OnMenuProcessor {

    DuelDiskView view;

    public PlayOnMenuProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onMenuPrepare(Menu menu) {
        menu.clear();
        Duel duel = view.getDuel();
        Item item = (Item) duel.getCurrentSelectItem();
        Container container = LayoutUtils.itemContainer(duel, item);

        // not duel disk?

        if(container instanceof Field) {
            if(item instanceof Card || item instanceof OverRay) {
                Card card;
                if(item instanceof OverRay) {
                    card = ((OverRay) item).getOverRayCards().topCard();
                } else {
                    card = (Card) item;
                }
                if(!card.isToken()) {
                    fieldCardMenu(menu);
                }
            }
            if(item instanceof Deck) {
                Deck deck = (Deck) item;
                if(deck.getName().equals(Utils.s(R.string.DECK))) {
                    mainDeckMenu(menu);
                }
                if(deck.getName().equals(Utils.s(R.string.TEMPORARY))) {
                    tempMenu(menu);
                }
            }
        } else if(container instanceof HandCards && item instanceof Card) {
            handCardsMenu(menu);
        } else {
            systemMenu(menu);
        }

        return true;
    }

    private void fieldCardMenu(Menu menu) {
        menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, Utils.s(R.string.MENU_CARD_BACK_TO_BOTTOM_OF_DECK));
        menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_CLOSE_BANISH, 0, Utils.s(R.string.MENU_CARD_CLOSE_BANISH));
    }

    private void mainDeckMenu(Menu menu) {
        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SHUFFLE, 0, Utils.s(R.string.MENU_DECK_SHUFFLE));
        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_CLOSE_BANISH_TOP, 0, Utils.s(R.string.MENU_DECK_CLOSE_BANISH_TOP));
        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_REVERSE, 0, Utils.s(R.string.MENU_DECK_REVERSE));
    }

    private void tempMenu(Menu menu) {
        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SHUFFLE, 0, Utils.s(R.string.MENU_DECK_SHUFFLE));
    }

    private void handCardsMenu(Menu menu) {
        menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, Utils.s(R.string.MENU_CARD_BACK_TO_BOTTOM_OF_DECK));
        menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_CLOSE_BANISH, 0, Utils.s(R.string.MENU_CARD_CLOSE_BANISH));
        menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_SHOW_HAND, 0, Utils.s(R.string.MENU_SHOW_HAND));
        menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_HIDE_HAND, 0, Utils.s(R.string.MENU_HIDE_HAND));
        menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_SHUFFLE_HAND, 0, Utils.s(R.string.MENU_SHUFFLE_HAND));
    }

    private void systemMenu(Menu menu) {
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_RESTART, 0, Utils.s(R.string.MENU_RESTART));
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_CHANGE_DECK, 0, Utils.s(R.string.MENU_CHANGE_DECK));
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_SIDE, 0, Utils.s(R.string.MENU_SIDE));
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_CARD_SEARCH, 0, Utils.s(R.string.MENU_CARD_SEARCH));
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_DECK_BUILDER, 0, Utils.s(R.string.MENU_DECK_BUILDER));
        SubMenu toggleMenu = menu.addSubMenu(Const.MENU_GROUP_MAIN, Const.MENU_TOGGLE, 0, Utils.s(R.string.MENU_TOGGLE));
        toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_GRAVITY_TOGGLE, 0, toggleMenuTxt(Utils.s(R.string.MENU_GRAVITY_TOGGLE), Configuration.PROPERTY_GRAVITY_ENABLE));
        toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_AUTO_SHUFFLE_TOGGLE, 0, toggleMenuTxt(Utils.s(R.string.MENU_AUTO_SHUFFLE_TOGGLE), Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE));
        toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_AUTO_DB_UPGRADE, 0, toggleMenuTxt(Utils.s(R.string.MENU_AUTO_DB_UPGRADE), Configuration.PROPERTY_AUTO_DB_UPGRADE));
        if (Utils.getSDK() >= 10) {
            menu.add(Const.MENU_GROUP_MAIN, Const.MENU_MIRROR_DISPLAY, 0, Utils.s(R.string.MENU_MIRROR_DISPLAY));
        }
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_HINT, 0, Utils.s(R.string.MENU_HINT));
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_FEEDBACK, 0, Utils.s(R.string.MENU_FEEDBACK));
        menu.add(Const.MENU_GROUP_MAIN, Const.MENU_EXIT, 0, Utils.s(R.string.MENU_EXIT));
    }

    private String toggleMenuTxt(String txt, String toggle) {
        boolean toggleTxt = Configuration.configProperties(toggle);
        return txt + "(" + (toggleTxt ? Utils.s(R.string.MENU_TOGGLE_ON) : Utils.s(R.string.MENU_TOGGLE_OFF)) + ")";
    }

    public boolean onMenuClick(MenuItem menuItem) {
        Duel duel = view.getDuel();
        MenuClick menuClick = new MenuClick(duel, menuItem);
        List<Action> actionChain = new MenuClickDispatcher().dispatch(menuClick);
        for(Action action : actionChain) {
            action.execute();
        }
        view.updateActionTime();
        return true;
    }
}
