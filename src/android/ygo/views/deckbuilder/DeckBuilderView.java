package android.ygo.views.deckbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;
import android.ygo.core.Card;
import android.ygo.layout.GridLayout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;
import android.ygo.views.YGOView;

import java.util.List;

public class DeckBuilderView extends YGOView {

    private DeckGestureDetector mGestureDetector;

    private GridLayout mainLayout;
    private GridLayout exLayout;
    private GridLayout sideLayout;

    public DeckBuilderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new DeckGestureDetector(new DeckGestureListener(this));
        mainLayout = new GridLayout(null, Utils.deckBuilderWidth(), 3, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        exLayout = new GridLayout(null, Utils.deckBuilderWidth(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        sideLayout = new GridLayout(null, Utils.deckBuilderWidth(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
    }

    public void loadDeck(String deck) {
        List<List<Card>> cards = Utils.getDbHelper().loadFromFile(deck);
        List<Card> mainCards = cards.get(0);
        List<Card> exCards = cards.get(1);
        List<Card> sideCards = cards.get(2);
        mainLayout.setCards(mainCards);
        exLayout.setCards(exCards);
        sideLayout.setCards(sideCards);
    }

    public void saveToDeck(String deck) {
        boolean saved = Utils.getDbHelper().saveToFile(deck, mainLayout.cards(), exLayout.cards(), sideLayout.cards());
        String info = "已保存["+deck+"].";
        if(!saved) {
            info = "保存["+deck+"]失败.";
        }
        Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void doDraw(Canvas canvas) {
        drawBackground(canvas);
        drawShadow(canvas);
        drawDeck(canvas);

        drawVersion(canvas);
        if (Configuration.configProperties(Configuration.PROPERTY_FPS_ENABLE)) {
            drawFPS(canvas);
        }
    }

    private void drawShadow(Canvas canvas) {
        Utils.DrawHelper helper = new Utils.DrawHelper(Utils.deckBuilderWidth(), 0);
        Paint paint = new Paint();

        paint.setColor(Configuration.fieldColor());
        paint.setAlpha(80);
        helper.drawRect(canvas, new Rect(0, 0, Utils.screenWidth() - Utils.deckBuilderWidth(), Utils.screenHeight()), paint);

        paint.setColor(Configuration.fontColor());
        helper.drawLine(canvas, 0, 5, 0, Utils.screenHeight() - 5, paint);

    }

    private void drawDeck(Canvas canvas) {
        int padding = 3;
        Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
        helper.drawDrawable(canvas, mainLayout, 0, 0);
        helper.drawDrawable(canvas, exLayout, 0, mainLayout.height() + padding);
        helper.drawDrawable(canvas, sideLayout, 0, mainLayout.height() + exLayout.height() + padding * 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
