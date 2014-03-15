package org.msk86.ygoroid.views.sidechanger;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.impl.builder.SideChanger;
import org.msk86.ygoroid.views.OnKeyProcessor;
import org.msk86.ygoroid.views.OnMenuProcessor;
import org.msk86.ygoroid.views.YGOView;

public class SideChangerView extends YGOView {

    private GestureDetector mGestureDetector;

    private SideChanger sideChanger;

    public SideChangerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sideChanger = new SideChanger();
        mGestureDetector = new GestureDetector(context, new SideGestureListener(this));
    }

    public void loadDeck(DeckCards cards) {
        sideChanger.loadDeck(cards);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    @Override
    public void doDraw(Canvas canvas) {
        drawBackground(canvas);
        sideChanger.getRenderer().draw(canvas, 0, 0);
//        drawVersion(canvas);
    }

    OnKeyProcessor onKeyProcessor;
    @Override
    public OnKeyProcessor getOnKeyProcessor() {
        if(onKeyProcessor == null) {
            onKeyProcessor = new SideOnKeyProcessor(this);
        }
        return onKeyProcessor;
    }

    @Override
    public OnMenuProcessor getOnMenuProcessor() {
        return null;
    }

    @Override
    public String getDuelState() {
        return YGOView.DUEL_STATE_SIDE;
    }

    @Override
    public Item exportData() {
        return sideChanger;
    }

    @Override
    public void importData(Item item) {
        this.sideChanger = (SideChanger) item;
    }

    public SideChanger getSideChanger() {
        return sideChanger;
    }


    @Override
    public void deallocateMemory() {
        sideChanger.recycleUselessBmp();
    }
}
