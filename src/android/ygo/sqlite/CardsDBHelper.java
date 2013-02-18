package android.ygo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.ygo.core.Card;
import android.ygo.utils.Configuration;

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

    private Card loadById(SQLiteDatabase database, String cardID) {
        Cursor c = database.query("texts t, datas d",
                new String[]{"t.id", "t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type"},
                "t.id = d.id and t.id = ?", new String[]{cardID}, null, null, null);
        Card card;
        if (c.getCount() == 1) {
            c.moveToFirst();
            card = new Card(c.getString(0), c.getString(1), c.getString(2), c.getInt(8),
                    c.getInt(7), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4));
        } else {
            card = new Card(cardID, "ID" + cardID, "卡片ID不存在！您可能需要更新数据库文件！", 0, 0, 0, 0, 0, 0);
        }
        return card;
    }

    private Card loadByName(SQLiteDatabase database, String cardName) {
        Cursor c = database.query("texts t, datas d",
                new String[]{"t.id", "t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type"},
                "t.id = d.id and t.name = ?", new String[]{cardName}, null, null, null);
        Card card;
        if (c.getCount() == 1) {
            c.moveToFirst();
            card = new Card(c.getString(0), c.getString(1), c.getString(2), c.getInt(8),
                    c.getInt(7), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4));
        } else {
            card = new Card("0", cardName, "卡片不存在！您可能需要更新数据库文件！", 0, 0, 0, 0, 0, 0);
        }
        return card;
    }

    public Card loadById(String cardID) {
        Card card;
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            card = loadById(database, cardID);
            database.close();
        } catch (Exception e) {
            card = new Card(cardID, "ID" + cardID, "数据库文件不存在！", 0, 0, 0, 0, 0, 0);
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

    public List<Card> loadById(List<String> cardIDs) {
        List<Card> cards = new ArrayList<Card>();
        for (String id : cardIDs) {
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
}
