package android.ygo.core;

import android.graphics.*;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.ygo.R;
import android.ygo.core.tool.BmpCache;
import android.ygo.core.tool.QuickFix;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.List;

public class Card implements SelectableItem, Drawable, Bmpable {
    public static Bitmap CARD_PROTECTOR = null;

    static {
        CARD_PROTECTOR = Utils.readBitmapScaleByHeight(Configuration.texturePath() + "cover" + Configuration.cardImageSuffix(), Utils.cardHeight());
        if (CARD_PROTECTOR == null) {
            CARD_PROTECTOR = Utils.readBitmapScaleByHeight(R.raw.cover, Utils.cardHeight());
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
    int category;
    boolean set = false;
    boolean positive = true;

    int indicator = 0;

    BmpCache bmpCache;

    public Card(String id, String name, String desc) {
        this(id, name, desc, 0, 0, 0, 0, 0, 0);
    }


    public Card(String id, String name, String desc, int typeCode, int attrCode, int raceCode, int level, int atk, int def) {
        this(id, name, desc, typeCode, attrCode, raceCode, level, atk, def, "0", 0);
    }

    public Card(String id, String name, String desc, int typeCode, int attrCode, int raceCode, int level, int atk, int def, String aliasId, int category) {
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
        this.category = category;
        this.positive = true;
        this.set = false;
        bmpCache = new BmpCache(this);

        QuickFix.fix(this);
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

    public Bitmap bmp(int width, int height) {
        Bitmap cardPic = Utils.readBitmapScaleByHeight(Configuration.cardImgPath() + id + Configuration.cardImageSuffix(), height);
        if (cardPic == null) {
            cardPic = Utils.readBitmapScaleByHeight(Configuration.zipInnerPicPath() + id + Configuration.cardImageSuffix(),
                    Configuration.cardImgPath() + id + Configuration.cardImageSuffix(), height);
        }
        if (cardPic == null) {
            cardPic = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(cardPic);
            Paint paint = new Paint();

            Bitmap cardTypeBmp = cardTypeBmp(width, height);
            Utils.drawBitmapOnCanvas(canvas, cardTypeBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);

            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(cardNameFontSize(height));

            if (!subTypes.contains(CardSubType.SYNC)) {
                textPaint.setColor(Configuration.fontColor());
                textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());
            } else {
                textPaint.setColor(Configuration.syncFontColor());
            }
            textPaint.setAntiAlias(true);
            CharSequence cs = Utils.cutOneLine(name, textPaint, width);
            StaticLayout layout = new StaticLayout(cs, textPaint, width, Layout.Alignment.ALIGN_CENTER, 1, 0, true);

            canvas.translate(0, height / 20);
            layout.draw(canvas);
        }
        return cardPic;
    }

    @Override
    public void destroyBmp() {
        bmpCache.clear();
    }

    private int cardNameFontSize(int height) {
        if(height <= Utils.cardHeight()) {
            return height / 10;
        } else {
            return height / 15;
        }
    }

    private Bitmap cardTypeBmp(int width, int height) {
        Bitmap typeBmp = type.bmpCache.get(width, height);
        if (typeBmp != null) {
            return typeBmp;
        }

        for (int i = subTypes.size() - 1; i >= 0; i--) {
            CardSubType subType = subTypes.get(i);
            Bitmap subTypeBmp = subType.bmpCache.get(width, height);
            if (subTypeBmp != null) {
                return subTypeBmp;
            }
        }

        Bitmap bmp = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(Color.GRAY);
        return bmp;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        Bitmap cardBmp = bmpCache.get(Utils.cardWidth(), Utils.cardHeight());
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
        textPaint.setTextSize(Utils.unitLength() / 8);
        textPaint.setColor(Configuration.fontColor());
        textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());
        textPaint.setAntiAlias(true);
        StaticLayout layout = new StaticLayout(String.valueOf(indicator), textPaint, width, Layout.Alignment.ALIGN_CENTER, 1, 0, true);
        while (layout.getLineCount() > 1) {
            textPaint.setTextSize(textPaint.getTextSize() * 0.9f);
            layout = new StaticLayout(String.valueOf(indicator), textPaint, width, Layout.Alignment.ALIGN_CENTER, 1, 0, true);
        }
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
        tokenSerial = 0;
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
        if (result.length() > 0) {
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

    public boolean isEx() {
        if (subTypes.contains(CardSubType.XYZ)
                || subTypes.contains(CardSubType.SYNC)
                || subTypes.contains(CardSubType.FUSION)) {
            return true;
        }
        return false;
    }

    public boolean isToken() {
        return subTypes.contains(CardSubType.TOKEN);
    }

    public boolean isTokenable() {
        return (category & Const.CATEGORY_TOKEN) == Const.CATEGORY_TOKEN;
    }


    private int tokenSerial = 0;

    public String nextTokenId() {
        if (isTokenable()) {
            tokenSerial++;
        }
        return String.valueOf(Integer.valueOf(id) + tokenSerial);
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
