package org.msk86.ygoroid.newaction;

import org.msk86.ygoroid.newop.Operation;

import java.util.List;

public interface Dispatcher<T extends Operation> {
    public List<Action> dispatch(T op);
}
