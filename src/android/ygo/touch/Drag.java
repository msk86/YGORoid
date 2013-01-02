package android.ygo.touch;

import android.ygo.core.Duel;
import android.ygo.core.Item;
import android.ygo.core.SelectableItem;

public class Drag {
    int fromX, fromY;
    int x, y;
    int toX, toY;
    private Duel duel;
    SelectableItem item;
    Item target;

    public Drag(Duel duel, int fx, int fy) {
        this.duel = duel;
        fromX = fx;
        fromY = fy;
        item = duel.itemAt(fx, fy);
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Item dropTo(int tx, int ty) {
        toX = tx;
        toY = ty;
        if(duel.inHand(tx, ty)){
            target = duel.getHandCards();
        } else if(duel.inDuelFields(tx, ty)) {
            target = duel.fieldAt(tx, ty);
        }
        return target;
    }
}
