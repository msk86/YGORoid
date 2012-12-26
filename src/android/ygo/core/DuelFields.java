package android.ygo.core;

import android.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class DuelFields implements Item {

    List<Field> fields = new ArrayList<Field>();
    public DuelFields() {
        for(int i=0;i<8;i++) {
            Field field = new Field(i);
            fields.add(field);
        }
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(800, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        for(Field field : fields) {
            Bitmap fImg = field.toBitmap();
            canvas.drawBitmap(fImg, fImg.getWidth() * field.index, 200, paint);
        }
        return bitmap;
    }
}
