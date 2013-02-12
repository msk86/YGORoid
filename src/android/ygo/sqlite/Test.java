package android.ygo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    private Context context;

    public Test(Context context) {
        this.context = context;
    }

    public void query() {
        CardsDBHelper helper = new CardsDBHelper(context, 1);

        List<String> ids = new ArrayList<String>();
        ids.add("39272762");
        ids.add("39272762");
        ids.add("35952884");
        ids.add("35952884");
        ids.add("84013237");
        ids.add("46986414");

        helper.loadCard(ids);
    }
}
