package android.ygo.views;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.ygo.core.*;

public class PlayMenuProcessor {

    DuelDiskView view;

    public PlayMenuProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onMenuPrepare(Menu menu) {
        Duel duel = view.getDuel();
        SelectableItem item = duel.getCurrentSelectItem();
        if(duel.getCardSelector() != null) {
            if(item instanceof Card) {
                menu.add("回卡组底");
            } else if (item instanceof Overlay) {

            }
            else if(item instanceof Deck) {
                if(((Deck)item).getName().equals("DECK")) {
                    menu.add("卡组翻转");
                    menu.add("");
                    menu.add("重新开始");
                    menu.add("更换卡组");
                }
            }
        }
        return true;
    }

    public boolean onMenuClick(int id, MenuItem menuItem) {
        return true;
    }
}
