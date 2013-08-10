package android.ygo.views.deckbuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.ygo.R;
import android.ygo.core.Card;
import android.ygo.core.DeckChecker;
import android.ygo.layout.GridLayout;
import android.ygo.layout.Layout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;
import android.ygo.views.YGOView;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilderView extends YGOView {

    private static final int PADDING = 3;
    public static final String TEMP_YDK = "_temp_.ydk";

    private GestureDetector mGestureDetector;

    private GridLayout mainLayout;
    private GridLayout exLayout;
    private GridLayout sideLayout;

    private boolean isMain = true;

    private CardNameList cardNameList;

    private String orgDeckName;
    private String currentDeckName;

    private EditText saveAsEdit;
    private AlertDialog saveAsDialog;
    private FrameLayout frameLayout;
    private Card currentSelectCard;

    public DeckBuilderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        orgDeckName = null;
        currentDeckName = null;
        cardNameList = new CardNameList(this);
        mGestureDetector = new GestureDetector(new DeckGestureListener(this));
        mainLayout = new GridLayout(null, Utils.deckBuilderWidth(), 3, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        exLayout = new GridLayout(null, Utils.deckBuilderWidth(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        sideLayout = new GridLayout(null, Utils.deckBuilderWidth(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        initSaveAsDialog();
    }

    public void newDeck() {
        mainLayout.setCards(new ArrayList<Card>());
        exLayout.setCards(new ArrayList<Card>());
        sideLayout.setCards(new ArrayList<Card>());
        setCurrentDeckName(null);
    }

    private void setCurrentDeckName(String name) {
        if (name == null) {
            currentDeckName = null;
            return;
        }
        orgDeckName = name;
        currentDeckName = name.substring(0, name.lastIndexOf('.'));
    }

    public String getCurrentDeckName() {
        if (currentDeckName == null) {
            return null;
        }
        return currentDeckName + ".ydk";
    }

    public void loadDeck(String deck) {
        newDeck();
        updateActionTime();
        List<List<Card>> cards = Utils.getDbHelper().loadFromFile(deck);
        mainLayout.setCards(cards.get(0));
        exLayout.setCards(cards.get(1));
        sideLayout.setCards(cards.get(2));
        setCurrentDeckName(deck);
        updateActionTime();
    }

    public void saveAs() {
        if (currentDeckName == null) {
            saveAsEdit.setText("");
        } else {
            saveAsEdit.setText(currentDeckName);
        }
        saveAsDialog.show();
    }

    public void save() {
        String deckName = getCurrentDeckName();
        if (deckName != null) {
            saveToDeck(deckName, true);
        } else {
            saveAs();
        }
    }

    private void saveToDeck(String deck, boolean autoRemove) {
        saveToDeck(deck, autoRemove, true);
    }
    private void saveToDeck(String deck, boolean autoRemove, boolean showTip) {
        boolean saved = Utils.getDbHelper().saveToFile(deck, mainLayout.cards(), exLayout.cards(), sideLayout.cards());
        String info = "已保存[" + deck + "].";
        if (!saved) {
            info = "保存[" + deck + "]失败.";
        } else if (autoRemove && orgDeckName != null && !orgDeckName.equals(deck)) {
            Utils.deleteDeck(orgDeckName);
        }
        if(showTip) {
            Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
        }
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
        updateActionTime();
    }

    private boolean checkCard(Card card) {
        String info = null;

        if (isMain) {
            if (!card.isEx()) {
                if (!DeckChecker.checkMainMax(mainLayout.cards(), true)) {
                    info = DeckChecker.ERROR_MAIN;
                }
            } else {
                if (!DeckChecker.checkEx(exLayout.cards(), true)) {
                    info = DeckChecker.ERROR_EX;
                }
            }
        } else {
            if (!DeckChecker.checkSide(sideLayout.cards(), true)) {
                info = DeckChecker.ERROR_SIDE;
            }
        }

        if (info == null) {
            List<Card> allCards = new ArrayList<Card>();
            allCards.addAll(mainLayout.cards());
            allCards.addAll(exLayout.cards());
            allCards.addAll(sideLayout.cards());

            if (!DeckChecker.checkSingleCard(allCards, card, true)) {
                String name = Utils.getDbHelper().loadNameById(Integer.parseInt(card.getRealId()));
                info = String.format(DeckChecker.ERROR_CARD, "[" + name + "]");
            }
        }

        if (info != null) {
            Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
        }

        return info == null;
    }

    @Override
    protected void doDraw(Canvas canvas) {
        drawBackground(canvas);
        drawShadow(canvas);
        drawDeck(canvas);
        drawHighLightMask(canvas);

        drawVersion(canvas);
        if (Configuration.configProperties(Configuration.PROPERTY_FPS_ENABLE)) {
            drawFPS(canvas);
        }
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
        Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
        helper.drawDrawable(canvas, mainLayout, 0, 0);
        helper.drawDrawable(canvas, exLayout, 0, mainLayout.height() + PADDING);
        helper.drawDrawable(canvas, sideLayout, 0, mainLayout.height() + exLayout.height() + PADDING * 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void changeDeck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
        builder.setTitle("请选择卡组");
        String[] decks = Utils.decks();
        final String[] deckList = new String[decks.length + 1];
        deckList[0] = "新卡组...";
        System.arraycopy(decks, 0, deckList, 1, decks.length);
        builder.setItems(deckList, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    newDeck();
                } else {
                    String deck = deckList[which];
                    loadDeck(deck);
                }
                updateActionTime();
                clearQueryText();
            }
        });
        builder.create().show();
    }


    @Override
    public void resume() {
        super.resume();
        registerSearchTextEvent();
        registerButtonEvent();
    }

    @Override
    public void pause() {
        saveToDeck(TEMP_YDK, false, false);
        super.pause();
    }

    private void clearQueryText() {
        EditText searchTextView = (EditText) Utils.getContext().findViewById(R.id.search_text);
        searchTextView.setText("");
        cardNameList.clearList();
    }

    private void registerSearchTextEvent() {
        EditText searchTextView = (EditText) Utils.getContext().findViewById(R.id.search_text);
        searchTextView.setOnEditorActionListener(new OnSearchTextEditorActionListener());
    }

    private void registerButtonEvent() {
        Button openBtn = (Button) Utils.getContext().findViewById(R.id.open_btn);
        openBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.OPEN_BTN));
        Button saveBtn = (Button) Utils.getContext().findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.SAVE_BTN));
        Button saveAsBtn = (Button) Utils.getContext().findViewById(R.id.save_as_btn);
        saveAsBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.SAVE_AS_BTN));
    }

    private void initSaveAsDialog() {
        saveAsEdit = new EditText(Utils.getContext());
        saveAsEdit.setGravity(Gravity.CENTER);
        saveAsEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        saveAsEdit.setSingleLine();

        frameLayout = new FrameLayout(Utils.getContext());
        frameLayout.addView(saveAsEdit, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        saveAsDialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle("请输入卡组名")
                .setPositiveButton("保存", new OnSaveAsClickListener("OK"))
                .setNegativeButton("取消", new OnSaveAsClickListener("Cancel"))
                .create();
        saveAsDialog.setView(frameLayout);


        saveAsEdit.setOnEditorActionListener(new OnSaveAsEditorActionListener());
        saveAsEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    saveAsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }

    private class OnSaveAsClickListener implements DialogInterface.OnClickListener {

        private String button;

        public OnSaveAsClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                currentDeckName = saveAsEdit.getText().toString();
                saveToDeck(getCurrentDeckName(), false);
            }
        }
    }

    private class OnSaveAsEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                currentDeckName = textView.getText().toString();
                saveToDeck(getCurrentDeckName(), false);
                saveAsDialog.hide();
            }

            return false;
        }
    }

    private class OnSearchTextEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                String text = textView.getText().toString();
                cardNameList.search(text);
            }

            return false;
        }
    }

    private class OnButtonClickListener implements OnClickListener {
        public static final int OPEN_BTN = 0;
        public static final int SAVE_BTN = 1;
        public static final int SAVE_AS_BTN = 2;

        private int button;

        private OnButtonClickListener(int button) {
            this.button = button;
        }

        @Override
        public void onClick(View view) {
            switch (button) {
                case OPEN_BTN:
                    changeDeck();
                    break;
                case SAVE_BTN:
                    save();
                    break;
                case SAVE_AS_BTN:
                    saveAs();
                    break;
            }
        }
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
        return x < sideLayout.width() && y >= mainLayout.height() + exLayout.height() + PADDING * 3;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }

    public void select(Card card) {
        if(card == null) {
            return;
        }
        if(currentSelectCard != null) {
            currentSelectCard.unSelect();
        }
        currentSelectCard = card;
        currentSelectCard.select();
    }
}
