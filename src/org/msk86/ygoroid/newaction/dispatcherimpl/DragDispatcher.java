package org.msk86.ygoroid.newaction.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newop.impl.Drag;

import java.util.ArrayList;
import java.util.List;

public class DragDispatcher implements Dispatcher<Drag> {
    @Override
    public List<Action> dispatch(Drag op) {
        List<Action> actionChain = new ArrayList<Action>();
        return actionChain;
    }
}
