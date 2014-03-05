package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.Infoable;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newcore.impl.InfoBar;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;

public class ShowCardEffectWindow extends BaseAction {
    public ShowCardEffectWindow(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof InfoBar) {
            Infoable infoable = ((InfoBar) item).getInfoItem();
            Card infoCard = null;
            if(infoable instanceof Card){
                infoCard = (Card) infoable;
            } if(infoable instanceof OverRay) {
                infoCard = ((OverRay) infoable).getOverRayCards().topCard();
            }
            if(infoCard != null) {
                CardEffectWindow window = new CardEffectWindow(duel, infoCard);
                duel.setCardEffectWindow(window);
            }
        }
    }
}
