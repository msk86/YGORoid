package android.ygo.views.deckbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;
import android.ygo.core.Card;
import android.ygo.core.DeckChecker;
import android.ygo.core.UserDefinedCard;
import android.ygo.layout.GridLayout;
import android.ygo.layout.Layout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;
import android.ygo.views.YGOView;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilderView extends YGOView {

    private DeckGestureDetector mGestureDetector;

    private GridLayout mainLayout;
    private GridLayout exLayout;
    private GridLayout sideLayout;

    private boolean isMain = true;

    private CardNameList cardNameList;

    public DeckBuilderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cardNameList = new CardNameList(this);
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

    public void addToDeck(Card card) {
        if(!checkCard(card)) {
            return;
        }

        Layout layout;

        if(isMain) {
            if(!card.isEx()) {
                layout = mainLayout;
            } else {
                layout = exLayout;
            }
        } else {
            layout = sideLayout;
        }


        Card clone = card.clone();
        layout.cards().add(clone);
        updateActionTime();
    }

    private boolean checkCard(Card card) {
        String info = null;

        if(isMain) {
            if(!card.isEx()) {
                if(!DeckChecker.checkMainMax(mainLayout.cards(), true)) {
                   info = DeckChecker.ERROR_MAIN;
                }
            } else {
                if(!DeckChecker.checkEx(exLayout.cards(), true)) {
                    info = DeckChecker.ERROR_EX;
                }
            }
        } else {
            if(!DeckChecker.checkSide(sideLayout.cards(), true)) {
                info = DeckChecker.ERROR_SIDE;
            }
        }

        if(info == null) {
            List<Card> allCards = new ArrayList<Card>();
            allCards.addAll(mainLayout.cards());
            allCards.addAll(exLayout.cards());
            allCards.addAll(sideLayout.cards());

            if(!DeckChecker.checkSingleCard(allCards, card, true)) {
                String name = Utils.getDbHelper().loadNameById(Integer.parseInt(card.getRealId()));
                info = String.format(DeckChecker.ERROR_CARD, "[" +name + "]");
            }
        }

        if(info != null) {
            Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
        }

        return info == null;
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

    public CardNameList getCardNameList() {
        return cardNameList;
    }
}
