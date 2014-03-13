package org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.*;
import org.msk86.ygoroid.newcore.constant.Const;
import org.msk86.ygoroid.newop.impl.MenuClick;
import org.msk86.ygoroid.utils.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MenuClickDispatcher implements Dispatcher<MenuClick> {
    @Override
    public List<Action> dispatch(MenuClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        switch (op.getMenuItem().getGroupId()) {
            case Const.MENU_GROUP_MAIN :
                switch (op.getMenuItem().getItemId()) {
                    case Const.MENU_RESTART:
                        actionChain.add(new RestartAction(op));
                        break;
                    case Const.MENU_CHANGE_DECK:
                        actionChain.add(new ChangeDeckAction(op));
                        break;
                    case Const.MENU_SIDE:
                        actionChain.add(new ToSideChangerAction(op));
                        break;
                    case Const.MENU_CARD_SEARCH:
                        actionChain.add(new SearchCardAction(op));
                        break;
                    case Const.MENU_DECK_BUILDER:
                        actionChain.add(new ToDeckBuilderAction(op));
                        break;
                    case Const.MENU_GRAVITY_TOGGLE:
                        actionChain.add(new ToggleAction(op, Configuration.PROPERTY_GRAVITY_ENABLE));
                        break;
                    case Const.MENU_FPS_TOGGLE:
                        actionChain.add(new ToggleAction(op, Configuration.PROPERTY_FPS_ENABLE));
                        break;
                    case Const.MENU_AUTO_SHUFFLE_TOGGLE:
                        actionChain.add(new ToggleAction(op, Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE));
                        break;
                    case Const.MENU_AUTO_DB_UPGRADE:
                        actionChain.add(new ToggleAction(op, Configuration.PROPERTY_AUTO_DB_UPGRADE));
                        break;
                    case Const.MENU_MIRROR_DISPLAY:
                        actionChain.add(new MirrorDisplayAction(op));
                        break;
                    case Const.MENU_HINT:
                        actionChain.add(new ShowHintAction(op));
                        break;
                    case Const.MENU_FEEDBACK:
                        actionChain.add(new FeedbackAction(op));
                        break;
                    case Const.MENU_EXIT:
                        actionChain.add(new ExitConfirmAction(op));
                        break;
                }
                break;

            case Const.MENU_GROUP_DECK :
                switch (op.getMenuItem().getItemId()) {
                    case Const.MENU_DECK_SHUFFLE:
                        actionChain.add(new ShuffleDeckAction(op));
                        break;
                    case Const.MENU_DECK_CLOSE_BANISH_TOP:
                        actionChain.add(new CloseBanishTopAction(op));
                        break;
                    case Const.MENU_DECK_REVERSE:
                        actionChain.add(new ReverseDeckAction(op));
                        break;
                }

                break;
            case Const.MENU_GROUP_FIELD_CARD :
                switch (op.getMenuItem().getItemId()) {
                    case Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK:
                        actionChain.add(new ToDeckBottomAction(op));
                        break;
                    case Const.MENU_CARD_CLOSE_BANISH:
                        actionChain.add(new CloseBanishCardAction(op));
                        break;
                }
                break;
            case Const.MENU_GROUP_HAND_CARD :
                switch (op.getMenuItem().getItemId()) {
                    case Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK:
                        actionChain.add(new ToDeckBottomAction(op));
                        break;
                    case Const.MENU_CARD_CLOSE_BANISH:
                        actionChain.add(new CloseBanishCardAction(op));
                        break;
                    case Const.MENU_SHOW_HAND:
                        actionChain.add(new ShowHandCardsAction(op));
                        break;
                    case Const.MENU_HIDE_HAND:
                        actionChain.add(new HideHandCardsAction(op));
                        break;
                    case Const.MENU_SHUFFLE_HAND:
                        actionChain.add(new ShuffleHandCardsAction(op));
                        break;
                }
                break;
        }

        return actionChain;
    }
}
