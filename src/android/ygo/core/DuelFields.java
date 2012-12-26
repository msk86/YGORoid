package android.ygo.core;

import android.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class DuelFields implements Item {

    List<Field> fields = new ArrayList<Field>();
    public DuelFields() {
        Field testField = new Field();
        fields.add(testField);
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        for(Field field : fields) {
            canvas.drawBitmap(field.toBitmap(), 200, 200, paint);
        }
        return bitmap;
    }
}
