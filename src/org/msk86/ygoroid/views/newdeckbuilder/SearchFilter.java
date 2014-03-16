package org.msk86.ygoroid.views.newdeckbuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.core.Attribute;
import org.msk86.ygoroid.core.CardSubType;
import org.msk86.ygoroid.core.CardType;
import org.msk86.ygoroid.core.Race;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.deckbuilder.actionimpl.SearchDeckAction;
import org.msk86.ygoroid.utils.Utils;
import org.msk86.ygoroid.views.newdeckbuilder.filter.*;

import java.util.ArrayList;
import java.util.List;

public class SearchFilter {
    View searchFilterView;
    AlertDialog dialog;
    SearchResultList searchResultList;
    boolean created;

    public SearchFilter(SearchResultList searchResultList) {
        this.searchResultList = searchResultList;
        this.created = false;
    }

    private void getDialogInstance() {
        if (dialog == null) {
            create();
        }
    }

    private void create() {
        created = true;
        LayoutInflater layoutInflater = Utils.getContext().getLayoutInflater();
        searchFilterView = layoutInflater.inflate(R.layout.search_filter, null);

        this.dialog = new AlertDialog.Builder(Utils.getContext())
                .setView(searchFilterView)
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Action action = new SearchDeckAction();
                        action.execute();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clear();
                        dialog.dismiss();
                    }
                })
                .create();
        createSpinnerValues();
        registerInputEvent();
    }

    private void registerInputEvent() {
        EditText minAtk = (EditText) searchFilterView.findViewById(R.id.search_min_atk);
        minAtk.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    EditText maxAtk = (EditText) searchFilterView.findViewById(R.id.search_max_atk);
                    if (maxAtk.getText().length() == 0) {
                        maxAtk.setText(textView.getText());
                    }
                }
                return false;
            }
        });
        EditText minDef = (EditText) searchFilterView.findViewById(R.id.search_min_def);
        minDef.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    EditText maxDef = (EditText) searchFilterView.findViewById(R.id.search_max_def);
                    if (maxDef.getText().length() == 0) {
                        maxDef.setText(textView.getText());
                    }
                }
                return false;
            }
        });
    }

    private void initEnable() {
        Spinner searchSubType = (Spinner) searchFilterView.findViewById(R.id.search_sub_type);
        searchSubType.setEnabled(false);
        setMonsterRelatedEnable(false);
    }

    private void createSpinnerValues() {
        createSearchTypeValues();
        createSearchRaceValues();
        createSearchAttrValues();
    }

    private void createSearchTypeValues() {
        Spinner searchType = (Spinner) searchFilterView.findViewById(R.id.search_type);
        ArrayAdapter<CardType> adapter = new ArrayAdapter<CardType>(Utils.getContext(), android.R.layout.simple_spinner_item, CardType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchType.setAdapter(adapter);

        searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CardType type = CardType.values()[i];
                Spinner searchSubType = (Spinner) searchFilterView.findViewById(R.id.search_sub_type);
                switch (type) {
                    case NULL:
                        searchSubType.setEnabled(false);
                        setMonsterRelatedEnable(false);
                        searchSubType.setSelection(0);
                        break;
                    case MONSTER:
                        searchSubType.setEnabled(true);
                        setMonsterRelatedEnable(true);
                        createSearchSubTypeValues(new CardSubType[]{CardSubType.NULL, CardSubType.NORMAL, CardSubType.EFFECT, CardSubType.TUNER, CardSubType.FUSION, CardSubType.RITUAL, CardSubType.SYNC, CardSubType.XYZ, CardSubType.DUAL, CardSubType.FLIP, CardSubType.SPIRIT, CardSubType.TOON, CardSubType.UNION});
                        break;
                    case SPELL:
                        searchSubType.setEnabled(true);
                        setMonsterRelatedEnable(false);
                        createSearchSubTypeValues(new CardSubType[]{CardSubType.NULL, CardSubType.NORMAL, CardSubType.QUICK_PLAY, CardSubType.EQUIP, CardSubType.RITUAL, CardSubType.FIELD, CardSubType.CONTINUOUS});
                        break;
                    case TRAP:
                        searchSubType.setEnabled(true);
                        setMonsterRelatedEnable(false);
                        createSearchSubTypeValues(new CardSubType[]{CardSubType.NULL, CardSubType.NORMAL, CardSubType.CONTINUOUS, CardSubType.COUNTER});
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setMonsterRelatedEnable(boolean enable) {
        Spinner searchRace = (Spinner) searchFilterView.findViewById(R.id.search_race);
        searchRace.setEnabled(enable);
        searchRace.setSelection(0);
        Spinner searchAttr = (Spinner) searchFilterView.findViewById(R.id.search_attr);
        searchAttr.setEnabled(enable);
        searchAttr.setSelection(0);
        EditText level = (EditText) searchFilterView.findViewById(R.id.search_level);
        level.setEnabled(enable);
        level.setText("");
        EditText minAtk = (EditText) searchFilterView.findViewById(R.id.search_min_atk);
        minAtk.setEnabled(enable);
        minAtk.setText("");
        EditText maxAtk = (EditText) searchFilterView.findViewById(R.id.search_max_atk);
        maxAtk.setEnabled(enable);
        maxAtk.setText("");
        EditText minDef = (EditText) searchFilterView.findViewById(R.id.search_min_def);
        minDef.setEnabled(enable);
        minDef.setText("");
        EditText maxDef = (EditText) searchFilterView.findViewById(R.id.search_max_def);
        maxDef.setEnabled(enable);
        maxDef.setText("");

    }

    private void createSearchSubTypeValues(CardSubType[] subTypes) {
        Spinner searchSubType = (Spinner) searchFilterView.findViewById(R.id.search_sub_type);

        ArrayAdapter<CardSubType> adapter = new ArrayAdapter<CardSubType>(Utils.getContext(), android.R.layout.simple_spinner_item, subTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSubType.setAdapter(adapter);
    }

    private void createSearchRaceValues() {
        Spinner searchRace = (Spinner) searchFilterView.findViewById(R.id.search_race);
        ArrayAdapter<Race> adapter = new ArrayAdapter<Race>(Utils.getContext(), android.R.layout.simple_spinner_item, Race.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchRace.setAdapter(adapter);
    }

    private void createSearchAttrValues() {
        Spinner searchAttr = (Spinner) searchFilterView.findViewById(R.id.search_attr);
        ArrayAdapter<Attribute> adapter = new ArrayAdapter<Attribute>(Utils.getContext(), android.R.layout.simple_spinner_item, Attribute.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchAttr.setAdapter(adapter);
    }

    public void show() {
        getDialogInstance();
        clear();
        initEnable();
        dialog.show();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (Utils.screenWidth() * 0.95);
        dialog.getWindow().setAttributes(p);
    }

    public void clear() {
        if (!created) {
            return;
        }
        Spinner searchType = (Spinner) searchFilterView.findViewById(R.id.search_type);
        searchType.setSelection(0);
        Spinner searchSubType = (Spinner) searchFilterView.findViewById(R.id.search_sub_type);
        searchSubType.setSelection(0);
        Spinner searchRace = (Spinner) searchFilterView.findViewById(R.id.search_race);
        searchRace.setSelection(0);
        Spinner searchAttr = (Spinner) searchFilterView.findViewById(R.id.search_attr);
        searchAttr.setSelection(0);
        EditText level = (EditText) searchFilterView.findViewById(R.id.search_level);
        level.setText("");
        EditText minAtk = (EditText) searchFilterView.findViewById(R.id.search_min_atk);
        minAtk.setText("");
        EditText maxAtk = (EditText) searchFilterView.findViewById(R.id.search_max_atk);
        maxAtk.setText("");
        EditText minDef = (EditText) searchFilterView.findViewById(R.id.search_min_def);
        minDef.setText("");
        EditText maxDef = (EditText) searchFilterView.findViewById(R.id.search_max_def);
        maxDef.setText("");
        EditText effect = (EditText) searchFilterView.findViewById(R.id.search_effect);
        effect.setText("");
    }


    public List<CardFilter> genFilters() {
        if (!created) {
            return new ArrayList<CardFilter>();
        }
        List<CardFilter> filters = new ArrayList<CardFilter>();
        Spinner searchType = (Spinner) searchFilterView.findViewById(R.id.search_type);
        Spinner searchSubType = (Spinner) searchFilterView.findViewById(R.id.search_sub_type);
        filters.add(new TypeFilter((CardType) searchType.getSelectedItem(), (CardSubType) searchSubType.getSelectedItem()));

        Spinner searchRace = (Spinner) searchFilterView.findViewById(R.id.search_race);
        filters.add(new RaceFilter((Race) searchRace.getSelectedItem()));

        Spinner searchAttr = (Spinner) searchFilterView.findViewById(R.id.search_attr);
        filters.add(new AttrFilter((Attribute) searchAttr.getSelectedItem()));

        EditText effect = (EditText) searchFilterView.findViewById(R.id.search_effect);
        filters.add(new EffectFilter(effect.getText().toString()));

        EditText minAtk = (EditText) searchFilterView.findViewById(R.id.search_min_atk);
        EditText maxAtk = (EditText) searchFilterView.findViewById(R.id.search_max_atk);
        filters.add(new AtkFilter(minAtk.getText().toString(), maxAtk.getText().toString()));

        EditText minDef = (EditText) searchFilterView.findViewById(R.id.search_min_def);
        EditText maxDef = (EditText) searchFilterView.findViewById(R.id.search_max_def);
        filters.add(new DefFilter(minDef.getText().toString(), maxDef.getText().toString()));

        EditText level = (EditText) searchFilterView.findViewById(R.id.search_level);
        filters.add(new LevelFilter(level.getText().toString()));

        return filters;
    }
}
