package android.ygo.views.logo;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.ygo.core.Card;
import android.ygo.layout.Layout;
import android.ygo.views.deckbuilder.DeckBuilderView;

public class LogoGestureListener extends GestureDetector.SimpleOnGestureListener {
    LogoView view;

    public LogoGestureListener(LogoView view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        view.nextLogo();
        return true;
    }
}
