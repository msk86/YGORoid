package org.msk86.ygoroid.views.newdeckbuilder;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.deckbuilder.actionimpl.*;
import org.msk86.ygoroid.newutils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeckController {
    private DeckBuilderView deckBuilderView;

    public DeckController(DeckBuilderView deckBuilderView) {
        this.deckBuilderView = deckBuilderView;
    }

    public void registerEvent() {
        EditText searchTextView = (EditText) Utils.getContext().findViewById(R.id.search_text);
        searchTextView.setOnEditorActionListener(new OnSearchTextEditorActionListener());

        Button openBtn = (Button) Utils.getContext().findViewById(R.id.open_btn);
        openBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.OPEN_BTN));
        Button deleteBtn = (Button) Utils.getContext().findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.DELETE_BTN));
        Button saveBtn = (Button) Utils.getContext().findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.SAVE_BTN));
        Button saveAsBtn = (Button) Utils.getContext().findViewById(R.id.save_as_btn);
        saveAsBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.SAVE_AS_BTN));
        Button filterBtn = (Button) Utils.getContext().findViewById(R.id.search_filter_btn);
        filterBtn.setOnClickListener(new OnButtonClickListener(OnButtonClickListener.SEARCH_FILTER_BTN));
    }

    private class OnButtonClickListener implements View.OnClickListener {
        public static final int OPEN_BTN = 0;
        public static final int SAVE_BTN = 1;
        public static final int SAVE_AS_BTN = 2;
        public static final int DELETE_BTN = 3;
        public static final int SEARCH_FILTER_BTN = 4;

        private int button;

        private OnButtonClickListener(int button) {
            this.button = button;
        }

        @Override
        public void onClick(View view) {
            List<Action> actionChain = new ArrayList<Action>();
            switch (button) {
                case OPEN_BTN:
                    actionChain.add(new ChangeDeckAction(deckBuilderView));
                    break;
                case SAVE_BTN:
                    if(deckBuilderView.getDeckBuilder().getCards().getDeckName() == null) {
                        actionChain.add(new SaveDeckAsAction(deckBuilderView));
                    } else {
                        actionChain.add(new SaveDeckAction(deckBuilderView));
                    }
                    break;
                case SAVE_AS_BTN:
                    actionChain.add(new SaveDeckAsAction(deckBuilderView));
                    break;
                case DELETE_BTN:
                    actionChain.add(new DeleteDeckAction(deckBuilderView));
                    break;
                case SEARCH_FILTER_BTN:
                    actionChain.add(new OpenSearchFilterAction());
            }

            for(Action action: actionChain) {
                action.execute();
            }
        }
    }

    private class OnSearchTextEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                Action action = new SearchByTextAction();
                action.execute();
            }
            return false;
        }
    }
}
