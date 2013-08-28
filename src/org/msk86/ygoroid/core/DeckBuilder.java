package org.msk86.ygoroid.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.Toast;
import android.ygo.R;
import org.msk86.ygoroid.layout.GridLayout;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;
import org.msk86.ygoroid.layout.Layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckBuilder implements Drawable {
    public static final int PADDING = 3;

    private GridLayout mainLayout;
    private GridLayout exLayout;
    private GridLayout sideLayout;

    private boolean isMain = true;

    private String orgDeckName;
    private String currentDeckName;


    public DeckBuilder() {
        mainLayout = new GridLayout(null, width(), 3, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        exLayout = new GridLayout(null, width(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        sideLayout = new GridLayout(null, width(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());

    }


    public void newDeck() {
        mainLayout.setCards(new ArrayList<Card>());
        exLayout.setCards(new ArrayList<Card>());
        sideLayout.setCards(new ArrayList<Card>());
        setFullCurrentDeckName(null);
    }

    public void loadDeck(String deck) {
        List<List<Card>> cards = Utils.getDbHelper().loadFromFile(deck);
        mainLayout.setCards(cards.get(0));
        exLayout.setCards(cards.get(1));
        sideLayout.setCards(cards.get(2));
        setFullCurrentDeckName(Utils.untempifyDeck(deck));
    }

    private void setFullCurrentDeckName(String name) {
        if (name == null) {
            currentDeckName = null;
            return;
        }
        orgDeckName = name;
        currentDeckName = name.substring(0, name.lastIndexOf('.'));
    }

    public String getFullCurrentDeckName() {
        if (currentDeckName == null || currentDeckName.length() == 0) {
            return null;
        }
        return currentDeckName + ".ydk";
    }

    public String getCurrentDeckName() {
        return currentDeckName;
    }

    public String getOrgDeckName() {
        return orgDeckName;
    }

    public void saveToDeck(String deckWithPostfix, boolean autoRemove, boolean showTip) {
        boolean saved = Utils.getDbHelper().saveToFile(deckWithPostfix, mainLayout.cards(), exLayout.cards(), sideLayout.cards());
        String info = String.format(Utils.s(R.string.SAVE_SUCCESS),
                deckWithPostfix, mainLayout.cards().size(), exLayout.cards().size(), sideLayout.cards().size());
        if (!saved) {
            info = String.format(Utils.s(R.string.SAVE_FAIL), deckWithPostfix);
        } else if (autoRemove && orgDeckName != null && !orgDeckName.equals(deckWithPostfix)) {
            Utils.deleteDeck(orgDeckName);
            orgDeckName = deckWithPostfix;
        }
        if (showTip) {
            Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
        }
    }

    public void saveTempDeck() {
        Utils.clearAllTempDeck();
        saveToDeck(Utils.tempifyDeck(orgDeckName), false, false);
    }

    public void addToDeck(Card card) {
        if (!checkCard(card)) {
            return;
        }

        Layout layout;

        if (isMain) {
            if (!card.isEx()) {
                layout = mainLayout;
            } else {
                layout = exLayout;
            }
        } else {
            layout = sideLayout;
        }


        Card clone = card.clone();
        layout.cards().add(clone);
    }

    private boolean checkCard(Card card) {
        String info = null;

        if (isMain) {
            if (!card.isEx()) {
                if (!DeckChecker.checkMainMax(mainLayout.cards(), true)) {
                    info = String.format(Utils.s(R.string.ERROR_MAIN), mainLayout.cards().size());
                }
            } else {
                if (!DeckChecker.checkEx(exLayout.cards(), true)) {
                    info = String.format(Utils.s(R.string.ERROR_EX), exLayout.cards().size());
                }
            }
        } else {
            if (!DeckChecker.checkSide(sideLayout.cards(), true)) {
                info = String.format(Utils.s(R.string.ERROR_SIDE), sideLayout.cards().size());
            }
        }

        if (info == null) {
            List<Card> allCards = new ArrayList<Card>();
            allCards.addAll(mainLayout.cards());
            allCards.addAll(exLayout.cards());
            allCards.addAll(sideLayout.cards());

            if (!DeckChecker.checkSingleCard(allCards, card, true)) {
                String name = Utils.getDbHelper().loadNameById(Integer.parseInt(card.getRealId()));
                info = String.format(Utils.s(R.string.ERROR_CARD), "[" + name + "]");
            }
        }

        if (info != null) {
            Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
        }

        return info == null;
    }

    @Override
    public int width() {
        return Utils.deckBuilderWidth();
    }

    @Override
    public int height() {
        return Utils.screenHeight();
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawDeckBuilder(canvas);
        drawHighLightMask(canvas);
    }

    private void drawDeckBuilder(Canvas canvas) {
        Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
        helper.drawDrawable(canvas, mainLayout, 0, 0);
        helper.drawDrawable(canvas, exLayout, 0, mainLayout.height() + PADDING);
        helper.drawDrawable(canvas, sideLayout, 0, mainLayout.height() + exLayout.height() + PADDING * 3);
    }

    private void drawHighLightMask(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Configuration.fontColor());
        paint.setAlpha(40);

        if(isMain) {
            Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
            helper.drawRect(canvas, new Rect(0,0,Utils.deckBuilderWidth(), mainLayout.height() + exLayout.height() + PADDING), paint);
        } else {
            Utils.DrawHelper helper = new Utils.DrawHelper(0, mainLayout.height() + exLayout.height() + PADDING * 3);
            helper.drawRect(canvas, new Rect(0,0,Utils.deckBuilderWidth(), sideLayout.height()), paint);
        }
    }


    public void sortAllCards() {
        Collections.sort(mainLayout.cards(), new Card.CardComparator());
        Collections.sort(exLayout.cards(), new Card.CardComparator());
        Collections.sort(sideLayout.cards(), new Card.CardComparator());
    }


    public void shuffleAllCards() {
        Collections.shuffle(mainLayout.cards());
    }


    public Card cardAt(int x, int y) {
        if (isInMain(x, y)) {
            return mainLayout.cardAt(x, y);
        } else if (isInEx(x, y)) {
            return exLayout.cardAt(x, y - mainLayout.height() - PADDING);
        } else if (isInSide(x, y)) {
            return sideLayout.cardAt(x, y - mainLayout.height() - exLayout.height() - PADDING * 3);
        }
        return null;
    }
    public Layout layoutAt(int x, int y) {
        if (isInMain(x, y)) {
            return mainLayout;
        } else if (isInEx(x, y)) {
            return exLayout;
        } else if (isInSide(x, y)) {
            return sideLayout;
        }
        return null;
    }

    public boolean isInMain(int x, int y) {
        return x < mainLayout.width() && y < mainLayout.height();
    }

    public boolean isInEx(int x, int y) {
        return x < exLayout.width() && y >= mainLayout.height() + PADDING && y < mainLayout.height() + exLayout.height() + PADDING;
    }

    public boolean isInSide(int x, int y) {
        return x < sideLayout.width() && y >= mainLayout.height() + exLayout.height() + PADDING * 3 && y < Utils.screenHeight();
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }
}
