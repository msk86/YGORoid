package org.msk86.ygoroid.newaction.sidechanger.dispatcherimpl;

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


        return actionChain;
    }
}
