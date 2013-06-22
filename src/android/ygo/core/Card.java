package android.ygo.core;

import android.graphics.*;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.ygo.R;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.List;

public class Card implements SelectableItem, Drawable {
    public static Bitmap CARD_PROTECTOR = Utils.readBitmapScaleByHeight(R.raw.cover, Utils.cardHeight());

    static {
        try {
            CARD_PROTECTOR = Utils.readBitmapScaleByHeight(Configuration.texturePath() + "cover" + Configuration.cardImageSuffix(), Utils.cardHeight());
        } catch (Exception e) {
        }
    }

    private boolean selected = false;

    String id;
    String aliasId;
    String name;
    String desc;
    CardType type;

    List<CardSubType> subTypes;
    Attribute attribute;
    Race race;
    int level;
    String atk;
    String def;
    boolean set = false;
    boolean positive = true;

    int indicator = 0;

    Bitmap cardPic;

    public Card(String id, String name, String desc) {
        this(id, name, desc, 0, 0, 0, 0, 0, 0);
    }


    public Card(String id, String name, String desc, int typeCode, int attrCode, int raceCode, int level, int atk, int def) {
        this(id, name, desc, typeCode, attrCode, raceCode, level, atk, def, "0");
    }

    public Card(String id, String name, String desc, int typeCode, int attrCode, int raceCode, int level, int atk, int def, String aliasId) {
        this.id = id;
        this.aliasId = aliasId;
        this.name = name;
        this.desc = desc;
        this.type = CardType.getCardType(typeCode);
        this.subTypes = CardSubType.getCardSubType(typeCode);
        this.attribute = Attribute.getAttribute(attrCode);
        this.race = Race.getRace(raceCode);
        this.level = level;
        this.atk = atk >= 0 ? String.valueOf(atk) : "?";
        this.def = def >= 0 ? String.valueOf(def) : "?";
        this.positive = true;
        this.set = false;
        initCardPic();
    }

    public String getRealId() {
        return "0".equals(aliasId) ? id : aliasId;
    }

    public String getName() {
        return name;
    }

    public List<CardSubType> getSubTypes() {
        return subTypes;
    }

    public void flip() {
        set = !set;
        if (set) {
            clearIndicator();
        }
    }

    public void open() {
        set = false;
    }

    public void set() {
        clearIndicator();
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

    public Bitmap bigCardPic() {
        Bitmap bigCardPic = null;
        try {
            bigCardPic = Utils.readBitmapScaleByHeight(Configuration.cardImgPath() + id + Configuration.cardImageSuffix(), Utils.screenHeight());
        } catch (Exception e) {
            if (type.getBigCardBmp() != null) {
                bigCardPic = type.getBigCardBmp();
            } else {
                for (int i = subTypes.size() - 1; i >= 0; i--) {
                    CardSubType subType = subTypes.get(i);
                    if (subType.getBigCardBmp() != null) {
                        bigCardPic = subType.getBigCardBmp();
                    }
                }
            }
        }
        return bigCardPic;
    }

    private void initCardPic() {
        int height = Utils.cardHeight();
        if (cardPic == null) {
            try {
                cardPic = Utils.readBitmapScaleByHeight(Configuration.cardImgPath() + id + Configuration.cardImageSuffix(), height);
            } catch (Exception e) {
                cardPic = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(cardPic);
                Paint paint = new Paint();
                Bitmap cardTypeBmp = cardTypeBmp();
                Utils.drawBitmapOnCanvas(canvas, cardTypeBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
                CharSequence cs = longName();
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(Utils.unitLength() / 10);
                if(!subTypes.contains(CardSubType.SYNC)) {
                    textPaint.setColor(Configuration.fontColor());
                    textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());
                } else {
                    textPaint.setColor(Configuration.syncFontColor());
                }
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout(cs, textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 0, 0, true);
                canvas.translate(0, Utils.cardHeight() / 20);
                layout.draw(canvas);
            }
        }
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
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        Bitmap cardBmp = cardPic;
        if (set) {
            cardBmp = CARD_PROTECTOR;
        }
        if (!positive) {
            cardBmp = Utils.rotate(cardBmp, 90);
        }

        helper.drawBitmap(canvas, cardBmp, helper.center(width(), cardBmp.getWidth()),
                helper.center(height(), cardBmp.getHeight()), new Paint());

        drawIndicator(canvas, x, y);

        if (selected) {
            drawHighLight(canvas, x, y);
        }
    }

    private void drawIndicator(Canvas canvas, int x, int y) {
        if (indicator == 0) {
            return;
        }
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        int width = Utils.unitLength() / 6;

        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        Rect indicatorRect = new Rect(0, 0, width, width);
        int offsetX = Utils.unitLength() - width - 4;
        indicatorRect.offset(offsetX, 0);

        helper.drawRect(canvas, indicatorRect, paint);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(Utils.unitLength() / 10);
        textPaint.setColor(Configuration.fontColor());
        textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());
        textPaint.setAntiAlias(true);
        StaticLayout layout = new StaticLayout(String.valueOf(indicator), textPaint, width, Layout.Alignment.ALIGN_CENTER, 0, 0, true);
        helper.drawLayout(canvas, layout, offsetX, 0);


    }

    private void drawHighLight(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        Paint paint = new Paint();
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        Rect highLightRect;
        if (positive) {
            highLightRect = new Rect(0, 0, Utils.cardWidth(), Utils.cardHeight());
            highLightRect.offset(helper.center(width(), Utils.cardWidth()), 0);
        } else {
            highLightRect = new Rect(0, 0, Utils.cardHeight(), Utils.cardWidth());
            highLightRect.offset(0, helper.center(width(), Utils.cardWidth()));
        }
        helper.drawRect(canvas, highLightRect, paint);
    }

    public void addIndicator() {
        indicator++;
    }

    public void removeIndicator() {
        if (indicator == 0) {
            return;
        }
        indicator--;
    }

    public void clearIndicator() {
        indicator = 0;
    }


    @Override
    public int width() {
        return Utils.cardHeight();
    }

    @Override
    public int height() {
        return Utils.cardHeight();
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

    public String cardTypeDesc() {
        StringBuilder result = new StringBuilder();
        result.append(type.toString());
        for (CardSubType subType : subTypes) {
            result.append("|" + subType.toString());
        }
        if(result.length() > 0) {
            result.insert(0, "[");
            result.append("]");
        }
        return result.toString();
    }

    public String levelAndADDesc() {
        StringBuilder result = new StringBuilder();
        if (subTypes.contains(CardSubType.XYZ)) {
            result.append("R");
        } else {
            result.append("L");
        }
        result.append(level);
        result.append(" ");
        result.append(atk + "/" + def);
        return result.toString();
    }

    public String attrAndRaceDesc() {
        StringBuilder result = new StringBuilder();
        result.append(attribute.toString());
        result.append(" ");
        result.append(race.toString());
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name);
        result.append(" ");

        if (type == CardType.NULL) {
            result.append(desc);
        } else {
            result.append(cardTypeDesc());
            if (type != CardType.MONSTER) {
                return result.toString();
            }
            result.append(" ");
            result.append(levelAndADDesc());
            result.append(" ");
            result.append(attrAndRaceDesc());
        }
        return result.toString();
    }
}
