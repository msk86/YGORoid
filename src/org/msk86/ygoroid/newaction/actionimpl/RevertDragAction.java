package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Listable;
import org.msk86.ygoroid.newcore.impl.*;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newop.impl.Drag;

public class RevertDragAction extends BaseAction {
    public RevertDragAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Drag drag = (Drag) operation;
        Container from = drag.getStartDrag().getContainer();
        if(from instanceof Field) {
            Field field = (Field) from;
            Item fieldItem = field.getItem();
            if(fieldItem == null) {
                field.setItem(item);
            } else {
                if(fieldItem instanceof Deck && item instanceof Card) {
                    ((Deck) fieldItem).getCardList().push((Card) item);
                }
                if(fieldItem instanceof OverRay && item instanceof Card) {
                    ((OverRay) fieldItem).overRay((Card) item);
                }
            }
        }
        if(from instanceof HandCards && item instanceof Card) {
            ((HandCards) from).add((Card) item);
        }
        if(from instanceof CardSelector && item instanceof Card) {
            CardSelector selector = (CardSelector) from;
            Listable selectorSource = selector.getSource();
            if(selectorSource instanceof Deck) {
                ((Deck) selectorSource).getCardList().push((Card) item);
            }
            if(selectorSource instanceof OverRay) {
                ((OverRay) selectorSource).overRay((Card) item);
            }
        }
    }
}
