package android.ygo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.ygo.core.Card;
import android.ygo.utils.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CardsDBHelper extends SQLiteOpenHelper {

    Context context;

    public CardsDBHelper(Context context, int version) {
        super(context, Configuration.baseDir() + "cards.cdb", null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // NOTHING
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // NOTHING
    }

    private Card loadById(SQLiteDatabase database, int cardID) {
        String idStr = String.valueOf(cardID);
        Cursor c = database.query("texts t, datas d",
                new String[]{"t.id", "t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type"},
                "t.id = d.id and t.id = ?", new String[]{idStr}, null, null, null);
        Card card;
        if (c.getCount() > 0) {
            c.moveToFirst();
            card = createCard(c);
        } else {
            card = new Card(idStr, "ID" + idStr, "卡片ID不存在！您可能需要更新数据库文件！", 0, 0, 0, 0, 0, 0);
        }
        return card;
    }

    private Card loadByName(SQLiteDatabase database, String cardName) {
        Card card = loadByWholeName(database, cardName);
        if (card == null) {
            card = fuzzyLoadByName(database, cardName);
        }
        if (card == null) {
            card = fuzzyLoadByWord(database, cardName);
        }
        if (card == null) {
            card = new Card("0", cardName, "卡片不存在！您可能需要更新数据库文件！", 0, 0, 0, 0, 0, 0);
        }
        return card;
    }

    private Card loadByWholeName(SQLiteDatabase database, String cardName) {
        Cursor c = database.query("texts t, datas d",
                new String[]{"t.id", "t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type"},
                "t.id = d.id and t.name = ?", new String[]{cardName}, null, null, null);
        Card card = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            card = createCard(c);
        }
        return card;
    }

    private Card fuzzyLoadByName(SQLiteDatabase database, String name) {
        Cursor c = database.query("texts t, datas d",
                new String[]{"t.id", "t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type"},
                "t.id = d.id and t.name like ?", new String[]{"%" + name + "%"}, null, null, null);
        Card card = null;
        if (c.getCount() > 0) {
            for (int i = 1; i <= c.getCount(); i++) {
                c.move(i);
                String cardName = c.getString(1);
                if (cardName.startsWith(name) || cardName.endsWith(name)) {
                    card = createCard(c);
                    break;
                }
            }
            if (card == null) {
                c.moveToFirst();
                card = createCard(c);
            }
        }
        return card;
    }

    private Card fuzzyLoadByWord(SQLiteDatabase database, String name) {
        String[] parts = new String[name.length()];
        String sqlNamePart = "";
        for (int i = 0; i < parts.length; i++) {
            sqlNamePart += " and t.name like ?";
            parts[i] = "%" + name.charAt(i) + "%";
        }

        Cursor c = database.query("texts t, datas d",
                new String[]{"t.id", "t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type"},
                "t.id = d.id" + sqlNamePart, parts, null, null, null);
        Card card = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            card = createCard(c);
        }
        return card;
    }

    public Card loadById(int cardID) {
        Card card;
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            card = loadById(database, cardID);
            database.close();
        } catch (Exception e) {
            card = new Card(String.valueOf(cardID), "ID" + cardID, "数据库文件不存在！", 0, 0, 0, 0, 0, 0);
        }
        return card;
    }

    public Card loadByName(String cardName) {
        Card card;
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            card = loadByName(database, cardName);
            database.close();
        } catch (Exception e) {
            card = new Card("0", cardName, "数据库文件不存在！", 0, 0, 0, 0, 0, 0);
        }

        return card;
    }

    public List<Card> loadById(List<Integer> cardIDs) {
        List<Card> cards = new ArrayList<Card>();
        for (int id : cardIDs) {
            Card card = loadById(id);
            cards.add(card);
        }
        return cards;
    }

    public List<Card> loadByName(List<String> cardNames) {
        List<Card> cards = new ArrayList<Card>();
        for (String name : cardNames) {
            Card card = loadByName(name);
            cards.add(card);
        }
        return cards;
    }

    private Card createCard(Cursor c) {
        return new Card(c.getString(0), c.getString(1), c.getString(2), c.getInt(8),
                c.getInt(7), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4));
    }


    private static final int IN_MAIN = 1;
    private static final int IN_EX = 2;
    private static final int IN_SIDE = 3;

    public List<List<Card>> loadFromFile(String fileName) {
        File deckFile = new File(Configuration.deckPath() + fileName);
        List<Card> mainCardList = new ArrayList<Card>();
        List<Card> exCardList = new ArrayList<Card>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(deckFile), "UTF-8"));
            String line = "";
            int cardIn = IN_MAIN;
            do {
                line = reader.readLine();
                if (line.length() == 0) {
                    continue;
                }
                if (line.startsWith("#") || line.startsWith("!")) {
                    if (line.startsWith("#main")) {
                        cardIn = IN_MAIN;
                    } else if (line.startsWith("#ex")) {
                        cardIn = IN_EX;
                    } else if (line.startsWith("!side")) {
                        cardIn = IN_SIDE;
                    }
                } else {
                    Card card;
                    try {
                        int id = Integer.parseInt(line);
                        card = loadById(id);
                    } catch (Exception e) {
                        card = loadByName(line);
                    }
                    switch (cardIn) {
                        case IN_MAIN:
                            mainCardList.add(card);
                            break;
                        case IN_EX:
                            exCardList.add(card);
                            break;
                    }
                }

            } while (line != null);
        } catch (Exception e) {
        }
        List<List<Card>> cardsLists = new ArrayList<List<Card>>();
        cardsLists.add(mainCardList);
        cardsLists.add(exCardList);
        return cardsLists;
    }
}
