package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.op.Operation;

public class EffectMagicTrapAction extends BaseAction {
    public EffectMagicTrapAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        card.open();
        card.positive();
        ((Field) container).setItem(card);
        duel.select(card);
    }
}
