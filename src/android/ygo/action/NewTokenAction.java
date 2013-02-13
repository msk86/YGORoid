package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Const;
import android.ygo.core.Field;
import android.ygo.op.Operation;

public class NewTokenAction extends BaseAction {
    public NewTokenAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card token = new Card("0", "TOKEN", "TOKEN", Const.TYPE_TOKEN + Const.TYPE_MONSTER, Const.NULL, Const.NULL, 0, 0, 0);
        Field field = (Field)container;
        field.setItem(token);
    }
}
