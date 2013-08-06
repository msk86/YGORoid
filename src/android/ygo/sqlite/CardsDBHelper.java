package android.ygo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.ygo.core.Card;
import android.ygo.core.UserDefinedCard;
import android.ygo.utils.CharSet;
import android.ygo.utils.Configuration;
import android.ygo.utils.UnicodeReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardsDBHelper extends SQLiteOpenHelper {

    public static final String QUERY_TABLES = "texts t, datas d";
    public static final String[] QUERY_FIELDS = new String[]{"t.id", "t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type", "d.alias", "d.category"};
    private static final String DB_PATH = Configuration.baseDir() + "cards.cdb";
    Context context;

    public CardsDBHelper(Context context, int version) {
        super(context, DB_PATH, null, version);
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

    public List<String> loadNamesByIds(List<Integer> ids) {
        List<String> names = new ArrayList<String>();
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            for (int id : ids) {
                String name = loadNameById(database, id);
                if(name != null) {
                    names.add(name);
                }
            }
            database.close();
        } catch (Exception e) {
        }
        return names;
    }

    public String loadNameById(SQLiteDatabase database, int cardID) {
        String idStr = String.valueOf(cardID);
        Cursor c = database.query("texts", new String[] {"name"},
                "id = ?", new String[]{idStr}, null, null, null);
        if (c.getCount() == 1) {
            c.moveToFirst();
            return c.getString(0);
        }
        c.close();
        return null;

    }

    private Card loadById(SQLiteDatabase database, int cardID) {
        String idStr = String.valueOf(cardID);
        Cursor c = database.query(QUERY_TABLES, QUERY_FIELDS,
                "t.id = d.id and t.id = ?", new String[]{idStr}, null, null, null);
        Card card;
        if (c.getCount() == 1) {
            c.moveToFirst();
            card = createCard(c);
        } else {
            card = new Card(idStr, "ID" + idStr, "卡片不存在！您可能需要更新数据库文件！", 0, 0, 0, 0, 0, 0);
        }
        c.close();
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
        Cursor c = database.query(QUERY_TABLES, QUERY_FIELDS,
                "t.id = d.id and t.name = ?", new String[]{cardName}, null, null, null);
        Card card = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            card = createCard(c);
        }
        c.close();
        return card;
    }

    private Card fuzzyLoadByName(SQLiteDatabase database, String name) {
        Cursor c = database.query(QUERY_TABLES, QUERY_FIELDS,
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
        c.close();
        return card;
    }

    private Card fuzzyLoadByWord(SQLiteDatabase database, String name) {
        String[] parts = new String[name.length()];
        String sqlNamePart = "";
        for (int i = 0; i < parts.length; i++) {
            sqlNamePart += " and t.name like ?";
            parts[i] = "%" + name.charAt(i) + "%";
        }

        Cursor c = database.query(QUERY_TABLES, QUERY_FIELDS,
                "t.id = d.id" + sqlNamePart, parts, null, null, null);
        Card card = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            card = createCard(c);
        }
        c.close();
        return card;
    }

    public Card loadById(int cardID) {
        Card card;
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            card = loadById(database, cardID);
            database.close();
        } catch (Exception e) {
            card = new Card(String.valueOf(cardID), "ID" + cardID, "数据库["+DB_PATH.replace("mnt/", "")+"]错误或不存在！", 0, 0, 0, 0, 0, 0);
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
            card = new Card("0", cardName, "数据库["+DB_PATH.replace("mnt/", "")+"]错误或不存在！", 0, 0, 0, 0, 0, 0);
        }
        return card;
    }

    private Card createCard(Cursor c) {
        return new Card(c.getString(0), c.getString(1), c.getString(2), c.getInt(8),
                c.getInt(7), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4), c.getString(9),
                c.getInt(10));
    }


    private static final int IN_MAIN = 1;
    private static final int IN_EX = 2;
    private static final int IN_SIDE = 3;

    public List<List<Card>> loadFromFile(String fileName) {
        File deckFile = new File(Configuration.deckPath() + fileName);
        List<Card> mainCardList = new ArrayList<Card>();
        List<Card> exCardList = new ArrayList<Card>();
        List<Card> sideCardList = new ArrayList<Card>();
        try {
            // TODO 重构此处代码，不要重复再读取一次文件内容
            String code = CharSet.detect(deckFile);
            BufferedReader reader = new BufferedReader(new UnicodeReader(new FileInputStream(deckFile), code));
            String line = "";
            int cardIn = IN_MAIN;
            do {
                line = reader.readLine();
                if (line.length() == 0) {
                    continue;
                }
                if (line.startsWith("#") || line.startsWith("!")
                        || line.startsWith("=") || line.startsWith("$")) {
                    if (isMain(line)) {
                        cardIn = IN_MAIN;
                    } else if (isEx(line)) {
                        cardIn = IN_EX;
                    } else if (isSide(line)) {
                        cardIn = IN_SIDE;
                    }
                } else {
                    Card card;
                    try {
                        int id = Integer.parseInt(line);
                        card = loadById(id);
                    } catch (Exception e) {
                        String cardName = line.replaceAll("\\[", "").replaceAll("\\]", "");
                        if (cardName.indexOf("#") > 0) {
                            cardName = cardName.substring(0, cardName.indexOf("#"));
                        }
                        if (cardName.startsWith("?")) {
                            card = new UserDefinedCard(cardName.substring(1));
                        } else {
                            card = loadByName(cardName);
                        }
                    }
                    switch (cardIn) {
                        case IN_MAIN:
                            mainCardList.add(card);
                            break;
                        case IN_EX:
                            exCardList.add(card);
                            break;
                        case IN_SIDE:
                            sideCardList.add(card);
                            break;
                    }
                }

            } while (line != null);
            reader.close();
        } catch (Exception e) {
        }

        List<List<Card>> cardsLists = new ArrayList<List<Card>>();
        cardsLists.add(mainCardList);
        cardsLists.add(exCardList);
        cardsLists.add(sideCardList);
        return cardsLists;
    }

    public boolean saveToFile(String deck, List<Card> main, List<Card> ex, List<Card> side) {
        StringBuilder txt = new StringBuilder();

        txt.append(cardListTxt("#main", main));
        txt.append(cardListTxt("#ex", ex));
        txt.append(cardListTxt("!side", side));

        if(deck.indexOf('.') < 0) {
            deck += ".ydk";
        }
        String deckPath = Configuration.deckPath() + deck;

        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(deckPath));
            dos.writeBytes(txt.toString());
            dos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String cardListTxt(String listName, List<Card> side) {
        StringBuilder txt = new StringBuilder();
        txt.append(listName).append("\r\n");
        for (Card card : side) {
            txt.append(card.getId()).append("\r\n");
        }
        return txt.toString();
    }

    private boolean isSide(String line) {
        return line.startsWith("!side") || line.startsWith("##");
    }

    private boolean isEx(String line) {
        return line.startsWith("#ex") || line.startsWith("==");
    }

    private boolean isMain(String line) {
        return line.startsWith("#main");
    }
}
