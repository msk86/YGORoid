package android.ygo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.ygo.core.Card;
import android.ygo.core.Configuration;

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

    public List<Card> loadCard(List<String> cardIDs) {
        List<Card> cards = new ArrayList<Card>();
        CardsDBHelper helper = new CardsDBHelper(context, 1);
        SQLiteDatabase database = helper.getReadableDatabase();
        for(String id : cardIDs) {
            Cursor c = database.query("texts t, datas d",
                    new String[]{"t.id","t.name", "t.desc", "d.atk", "d.def", "d.race", "d.level", "d.attribute", "d.type"},
                    "t.id = d.id and t.id = ?", new String[]{id}, null, null, null);
            c.moveToFirst();
            Card card = new Card(c.getString(0), c.getString(1), c.getString(2));
            cards.add(card);
        }
        database.close();
        return cards;
    }
}
