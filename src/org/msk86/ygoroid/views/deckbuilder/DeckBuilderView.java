package org.msk86.ygoroid.views.deckbuilder;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.DeckBuilder;
import org.msk86.ygoroid.core.InfoWindow;
import org.msk86.ygoroid.core.ShowCardWindow;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;
import org.msk86.ygoroid.views.YGOView;

public class DeckBuilderView extends YGOView {

    private GestureDetector mGestureDetector;

    private CardNameList cardNameList;

    private DeckBuilder deckBuilder;

    private EditText saveAsEdit;
    private AlertDialog saveAsDialog;
    private FrameLayout frameLayout;
    private Card currentSelectCard;

    private InfoWindow infoWindow;
    private ShowCardWindow cardWindow;

    private SearchFilter filter;

    public DeckBuilderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        deckBuilder = new DeckBuilder();
        cardNameList = new CardNameList(this);
        mGestureDetector = new GestureDetector(new DeckGestureListener(this));
        initSaveAsDialog();

        filter = new SearchFilter(cardNameList);

        infoWindow = new InfoWindow(Utils.deckBuilderWidth());
    }

    public void newDeck() {
        deckBuilder.newDeck();
        infoWindow.clearInfo();
        cardWindow = null;
    }

    public void loadDeck(String deck) {
        newDeck();
        updateActionTime();
        deckBuilder.loadDeck(deck);
        updateActionTime();
    }

    public void saveAs() {
        if (deckBuilder.getCurrentDeckName() == null) {
            saveAsEdit.setText("");
        } else {
            saveAsEdit.setText(deckBuilder.getCurrentDeckName());
        }
        saveAsDialog.show();
    }

    public void save() {
        String deckName = deckBuilder.getFullCurrentDeckName();
        if (deckName != null) {
            deckBuilder.saveToDeck(deckName, true, true);
        } else {
            saveAs();
        }
    }

    public void addToDeck(Card card) {
        deckBuilder.addToDeck(card);
        updateActionTime();
    }


    @Override
    protected void doDraw(Canvas canvas) {
        drawBackground(canvas);
        drawShadow(canvas);
        deckBuilder.draw(canvas, 0, 0);

        Utils.DrawHelper helper = new Utils.DrawHelper(0, 0);
        helper.drawDrawable(canvas, infoWindow, helper.center(Utils.deckBuilderWidth(), infoWindow.width()), helper.bottom(Utils.screenHeight(), infoWindow.height()));

        if (cardWindow != null) {
            helper.drawDrawable(canvas, cardWindow, 0, 0);
        }

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void changeDeck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
        builder.setTitle(Utils.s(R.string.CHOOSE_DECK));
        String[] decks = Utils.decks();
        final String[] deckList = new String[decks.length + 1];
        deckList[0] = Utils.s(R.string.NEW_DECK);
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
        deckBuilder.saveTempDeck();
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

        Button filterBtn = (Button) Utils.getContext().findViewById(R.id.search_filter_btn);
        filterBtn.setOnClickListener(new OnSearchFilterClickListener());
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
                .setTitle(Utils.s(R.string.DECK_NAME))
                .setPositiveButton(Utils.s(R.string.CONFIRM_SAVE), new OnSaveAsClickListener("OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnSaveAsClickListener("Cancel"))
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

    public void sortAllCards() {
        deckBuilder.sortAllCards();
        updateActionTime();
    }

    public void shuffleAllCards() {
        deckBuilder.shuffleAllCards();
        updateActionTime();
    }

    private class OnSaveAsClickListener implements DialogInterface.OnClickListener {

        private String button;

        public OnSaveAsClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                deckBuilder.saveToDeck(saveAsEdit.getText().toString() + ".ydk", false, true);
            }
        }
    }

    private class OnSaveAsEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                deckBuilder.saveToDeck(textView.getText().toString()+".ydk", false, true);
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
                filter.clear();
                cardNameList.search(text);
            }
            return false;
        }
    }

    private class OnSearchFilterClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            filter.show();
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

    public boolean isInDeckBuilder(int x, int y) {
        return y < deckBuilder.height();
    }

    public boolean isInInfo(int x, int y) {
        return y >= Utils.screenHeight() - infoWindow.height();
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

        infoWindow.setInfo(currentSelectCard);

        if(cardWindow != null) {
            showCard(currentSelectCard);
        }
    }

    public InfoWindow getInfoWindow() {
        return infoWindow;
    }

    public void setCardWindow(ShowCardWindow cardWindow) {
        this.cardWindow = cardWindow;
    }

    public ShowCardWindow getCardWindow() {
        return cardWindow;
    }

    public DeckBuilder getDeckBuilder() {
        return deckBuilder;
    }

    public void showCard(Card card) {
        ShowCardWindow cardWindow = new ShowCardWindow(card, Utils.deckBuilderWidth());
        setCardWindow(cardWindow);
    }
}
