package org.msk86.ygoroid.newaction.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.actionimpl.DragFieldCardAction;
import org.msk86.ygoroid.newaction.actionimpl.DragHandCardAction;
import org.msk86.ygoroid.newaction.actionimpl.DragOverRayAction;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.*;
import org.msk86.ygoroid.newop.impl.StartDrag;

import java.util.ArrayList;
import java.util.List;

public class StartDragDispatcher implements Dispatcher<StartDrag> {
    @Override
    public List<Action> dispatch(StartDrag op) {
        List<Action> actionChain = new ArrayList<Action>();

        Item item = op.getItem();
        Container container = op.getContainer();

        if(container instanceof HandCards) {
            actionChain.add(new DragHandCardAction(op));
        }
        if(container instanceof Field) {
            if(item instanceof Card) {
                actionChain.add(new DragFieldCardAction(op));
            }
            if(item instanceof OverRay) {
                actionChain.add(new DragOverRayAction(op));
            }
//            if(item instanceof Deck) {
//                actionChain.add(DragDeckTopAction(op));
//            }
        }
//        if(container instanceof CardSelector) {
//            actionChain.add(DragCardSelectorCardAction(op));
//        }

        return actionChain;
    }
}
