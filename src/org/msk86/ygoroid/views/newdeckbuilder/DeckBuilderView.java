package org.msk86.ygoroid.views.newdeckbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
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

        searchResultList = new SearchResultList(this);
        controller = new DeckController(this);
    }

    public DeckBuilder getDeckBuilder() {
        return deckBuilder;
    }

    public DeckController getController() {
        return controller;
    }

    public SearchResultList getSearchResultList() {
        return searchResultList;
    }

    @Override
    protected void doDraw(Canvas canvas) {
        drawBackground(canvas);
        deckBuilder.getRenderer().draw(canvas, 0, 0);
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
    public void deallocateMemory() {
        deckBuilder.recycleUselessBmp();
    }
}
