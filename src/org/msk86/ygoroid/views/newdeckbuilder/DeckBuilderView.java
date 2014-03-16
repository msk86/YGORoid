package org.msk86.ygoroid.views.newdeckbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.builder.DeckBuilder;
import org.msk86.ygoroid.views.OnKeyProcessor;
import org.msk86.ygoroid.views.OnMenuProcessor;
import org.msk86.ygoroid.views.YGOView;

public class DeckBuilderView extends YGOView {
    private GestureDetector mGestureDetector;
    SearchResultList searchResultList;
    DeckController controller;

    private DeckBuilder deckBuilder;

    public DeckBuilderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        deckBuilder = new DeckBuilder();
        mGestureDetector = new GestureDetector(new DeckGestureListener(this));

        searchResultList = new SearchResultList(deckBuilder);
        controller = new DeckController(deckBuilder);
    }

    public DeckBuilder getDeckBuilder() {
        return deckBuilder;
    }

    @Override
    protected void doDraw(Canvas canvas) {
        drawBackground(canvas);
        deckBuilder.getRenderer().draw(canvas, 0, 0);
//        drawVersion(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    OnKeyProcessor onKeyProcessor;

    @Override
    public OnKeyProcessor getOnKeyProcessor() {
        if(onKeyProcessor == null) {
            onKeyProcessor = new DeckOnKeyProcessor(this);
        }
        return onKeyProcessor;
    }

    @Override
    public OnMenuProcessor getOnMenuProcessor() {
        return null;
    }

    @Override
    public String getDuelState() {
        return YGOView.DUEL_STATE_DECK;
    }

    @Override
    public Item exportData() {
        return deckBuilder;
    }

    @Override
    public void importData(Item item) {
        deckBuilder = (DeckBuilder) item;
    }

    @Override
    public void deallocateMemory() {
        deckBuilder.recycleUselessBmp();
    }




}
