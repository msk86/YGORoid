package org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.FlipCardAction;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.MoveCardToTempAction;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.NewTokenAction;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.OpenCardSelectorAction;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Controllable;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Listable;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.*;
import org.msk86.ygoroid.newop.impl.DoubleClick;
import org.msk86.ygoroid.newutils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DoubleClickDispatcher implements Dispatcher<DoubleClick> {
    @Override
    public List<Action> dispatch(DoubleClick op) {
        List<Action> actionChain = new ArrayList<Action>();
        Item item = op.getItem();
        Container container = op.getContainer();


        if(item instanceof InfoBar) {
            InfoBar infoBar = (InfoBar) item;
            if(infoBar.getInfoItem() instanceof Listable) {
                actionChain.add(new OpenCardSelectorAction(op));
            }
        }

        if(container instanceof HandCards) {
            if(item instanceof Controllable) {
                actionChain.add(new FlipCardAction(op));
            }
        }

        if(container instanceof Field) {
            Field field = (Field) container;
            if(item == null && field.getType() == FieldType.MONSTER) {
                actionChain.add(new NewTokenAction(op));
            }
            if(item instanceof Controllable) {
                actionChain.add(new FlipCardAction(op));
            }
            if(item instanceof Deck) {
                actionChain.add(new OpenCardSelectorAction(op));
            }
        }

        if(container instanceof CardSelector) {
            CardSelector selector = (CardSelector) container;
            Listable source = selector.getSource();
            if(source instanceof Deck) {
                Deck deck = (Deck) source;
                if(deck.getName().equals(Utils.s(R.string.TEMPORARY))) {
                    actionChain.add(new FlipCardAction(op));
                } else {
                    actionChain.add(new MoveCardToTempAction(op));
                }
            }
        }


        return actionChain;
    }
}
