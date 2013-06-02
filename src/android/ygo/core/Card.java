package android.ygo.core;

import android.ygo.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.List;

public class Card implements SelectableItem {
    public static final Bitmap CARD_PROTECTOR = Utils.readBitmapScaleByHeight(R.raw.cover, Utils.cardHeight());

    private boolean selected = false;

    String id;
    String name;
    String desc;
    CardType type;

    List<CardSubType> subTypes;
    Attribute attribute;
    Race race;
    int level;
    int atk;
    int def;
    boolean set = false;
    boolean positive = true;

    Bitmap cardPic;
    Bitmap highLight;

    public Card(String id, String name, String desc) {
        this(id, name, desc, 0, 0, 0, 0, 0, 0);
    }


    public Card(String id, String name, String desc, int typeCode, int attrCode, int raceCode, int level, int atk, int def) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.type = CardType.getCardType(typeCode);
        this.subTypes = CardSubType.getCardSubType(typeCode);
        this.attribute = Attribute.getAttribute(attrCode);
        this.race = Race.getRace(raceCode);
        this.level = level;
        this.atk = atk;
        this.def = def;
        this.positive = true;
        this.set = false;
        initCardPic();
    }

    public CardType getType() {
        return type;
    }

    public List<CardSubType> getSubTypes() {
        return subTypes;
    }

    public void flip() {
        set = !set;
    }

    public void open() {
        set = false;
    }

    public void set() {
        set = true;
    }

    public boolean isOpen() {
        return !set;
    }

    public void changePosition() {
        positive = !positive;
    }

    public void positive() {
        positive = true;
    }

    public void negative() {
        positive = false;
    }

    private void initCardPic() {
        int height = Utils.cardHeight();
        try {
            cardPic = Utils.readBitmapScaleByHeight(Configuration.cardImgPath() + id + ".jpg", height);
        } catch (Exception e) {
            cardPic = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(cardPic);
            Paint paint = new Paint();
            Bitmap cardTypeBmp = cardTypeBmp();
            Utils.drawBitmapOnCanvas(canvas, cardTypeBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
            CharSequence cs = longName();
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(Utils.unitLength() / 10);
            textPaint.setColor(Configuration.fontColor());
            textPaint.setShadowLayer(1, 0, 0, Color.BLACK);
            StaticLayout layout = new StaticLayout(cs, textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 0, 0, true);
            canvas.translate(0, Utils.cardHeight() / 20);
            layout.draw(canvas);
        }
        highLight = highLight();
    }

    private Bitmap cardTypeBmp() {
        if (type.getCardBmp() != null) {
            return type.getCardBmp();
        }

        for (int i = subTypes.size() - 1; i >= 0; i--) {
            CardSubType subType = subTypes.get(i);
            if (subType.getCardBmp() != null) {
                return subType.getCardBmp();
            }
        }

        Bitmap bmp = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(Color.GRAY);
        return bmp;
    }

    private String longName() {
        char[] cs = name.toCharArray();
        int charLen = 0;
        int maxCutFlag = 11;
        int cutIndex = cs.length;
        for (int i = 0; i < cs.length; i++) {
            int newLen = charLen + Utils.textPlace(cs[i]);
            if (newLen > maxCutFlag) {
                cutIndex = i;
                break;
            }
            charLen = newLen;
        }
        String nName = name.substring(0, cutIndex);
        if (cutIndex != name.length()) {
            nName += "...";
        }
        return nName;
    }

    @Override
    public Bitmap toBitmap() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
        Bitmap cardBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cardBmp);
        Paint paint = new Paint();
        if (set) {
            Utils.drawBitmapOnCanvas(canvas, CARD_PROTECTOR, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
        } else {
            Utils.drawBitmapOnCanvas(canvas, cardPic, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
        }

        if (selected) {
            Utils.drawBitmapOnCanvas(canvas, highLight, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
        }

        if (!positive) {
            cardBmp = Utils.rotate(cardBmp, 90);
        }
        return cardBmp;
    }

    private Bitmap highLight() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
        Bitmap highLightBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(highLightBmp);
        Paint paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(width, 0, width, height, paint);
        canvas.drawLine(width, height, 0, height, paint);
        canvas.drawLine(0, height, 0, 0, paint);
        return highLightBmp;
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public void unSelect() {
        selected = false;
    }

    @Override
    public boolean isSelect() {
        return selected;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name);
        result.append(" ");

        if (type == CardType.NULL) {
            result.append(desc);
        } else {
            result.append(type.toString());
            for (CardSubType subType : subTypes) {
                result.append("|" + subType.toString());
            }
            if (type != CardType.MONSTER) {
                return result.toString();
            }
            result.append(" ");
            if (subTypes.contains(CardSubType.XYZ)) {
                result.append("R");
            } else {
                result.append("L");
            }
            result.append(level);
            result.append(" ");
            result.append(atk + "/" + def);
            result.append(" ");
            result.append(attribute.toString());
            result.append(" ");
            result.append(race.toString());
        }
        return result.toString();
    }
}
