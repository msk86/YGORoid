package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.constant.Attribute;
import org.msk86.ygoroid.newcore.constant.CardSubType;
import org.msk86.ygoroid.newcore.constant.CardType;
import org.msk86.ygoroid.newcore.constant.Race;
import org.msk86.ygoroid.newcore.impl.bmp.CardGenerator;
import org.msk86.ygoroid.newcore.impl.renderer.CardRenderer;

import java.util.Comparator;
import java.util.List;

public class Card implements Item, Selectable, Controllable, Bmpable, Infoable {
    String id;
    String aliasId;
    String name;
    String desc;

    private int typeCode;
    CardType type;
    List<CardSubType> subTypes;

    private int attrCode;
    Attribute attribute;

    private int raceCode;
    Race race;

    int level;

    private int atkInt;
    private int defInt;
    String atk;
    String def;

    int category;

    boolean set = false;
    boolean positive = true;

    private Indicator indicator;

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
        this.typeCode = typeCode;
        this.type = CardType.getCardType(typeCode);
        this.subTypes = CardSubType.getCardSubType(typeCode);
        this.attrCode = attrCode;
        this.attribute = Attribute.getAttribute(attrCode);
        this.raceCode = raceCode;
        this.race = Race.getRace(raceCode);
        this.level = level & 0xF;
        this.atkInt = atk;
        this.defInt = def;
        this.atk = atk >= 0 ? String.valueOf(atk) : "?";
        this.def = def >= 0 ? String.valueOf(def) : "?";
        this.category = category;
        this.positive = true;
        this.set = false;

        this.indicator = new Indicator();
    }



    public String getId() {
        return id;
    }

    public String getRealId() {
        return "0".equals(aliasId) ? id : aliasId;
    }

    public String getName() {
        return name;
    }

    public int getCategory() {
        return category;
    }

    public CardType getType() {
        return type;
    }

    public List<CardSubType> getSubTypes() {
        return subTypes;
    }

    @Override
    public void flip() {
        if(set) open();
        else set();
    }

    @Override
    public void open() {
        set = false;
    }

    @Override
    public void set() {
        indicator.clear();
        set = true;
    }

    @Override
    public boolean isOpen() {
        return !set;
    }

    @Override
    public void rotate() {
        positive = !positive;
    }

    @Override
    public void positive() {
        positive = true;
    }

    @Override
    public void negative() {
        positive = false;
    }

    @Override
    public boolean isPositive() {
        return positive;
    }

    public boolean isToken() {
        return subTypes.contains(CardSubType.TOKEN);
    }

    public boolean isXYZ() {
        return subTypes.contains(CardSubType.XYZ);
    }

    public boolean isEx() {
        return subTypes.contains(CardSubType.FUSION) || subTypes.contains(CardSubType.SYNC)
                || subTypes.contains(CardSubType.XYZ);
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public String getDesc() {
        return desc;
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

    @Override
    public Card clone() {
        return new Card(id, name, desc, typeCode, attrCode, raceCode, level, atkInt, defInt, aliasId, category);
    }

    public String attrAndRaceDesc() {
        StringBuilder result = new StringBuilder();
        result.append(attribute.toString());
        result.append(" ");
        result.append(race.toString());
        return result.toString();
    }

    private Renderer renderer;

    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new CardRenderer(this);
        }
        return renderer;
    }

    private BmpGenerator generator;

    @Override
    public BmpGenerator getBmpGenerator() {
        if(generator == null) {
            generator = new CardGenerator(this);
        }
        return generator;
    }

    private boolean select;
    @Override
    public void select() {
        select = true;
    }

    @Override
    public void unSelect() {
        select = false;
    }

    @Override
    public boolean isSelect() {
        return select;
    }

    @Override
    public String getInfo() {
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


    public static class CardComparator implements Comparator<Card> {
        @Override
        public int compare(Card card1, Card card2) {
            // 怪 -> 魔 -> 陷
            if (card1.type.getCode() != card2.type.getCode()) {
                return card1.type.getCode() - card2.type.getCode();
            }

            // subtype 普 -> 效 -> 仪 -> 融 -> 同 -> 超; 速 -> 永 -> 装 -> 场 -> 反
            int card1SortType = sortSubTypeCode(card1);
            int card2SortType = sortSubTypeCode(card2);
            if (card1SortType != card2SortType) {
                return card1SortType - card2SortType;
            }

            // level 12 -> 1
            if (card1.level != card2.level) {
                return card2.level - card1.level;
            }
            // ATK MAX -> ?
            if (card1.atkInt != card2.atkInt) {
                return card2.atkInt - card1.atkInt;
            }
            // DEF MAX -> ?
            if (card1.defInt != card2.defInt) {
                return card2.defInt - card1.defInt;
            }
            // 地水火风光暗
            if (card1.attrCode != card2.attrCode) {
                return card1.attrCode - card2.attrCode;
            }
            // 种族
            if (card1.raceCode != card2.raceCode) {
                return card1.raceCode - card2.raceCode;
            }
            // 同名
            if (card1.getName().equals(card2.getName())) {
                return 0;
            }
            // ID
            return Integer.parseInt(card1.getRealId()) - Integer.parseInt(card2.getRealId());
        }

        public static int sortSubTypeCode(Card card) {
            CardSubType[] sortableSubTypes = {CardSubType.NORMAL, CardSubType.FUSION, CardSubType.RITUAL, CardSubType.SYNC, CardSubType.XYZ,
                    CardSubType.QUICK_PLAY, CardSubType.CONTINUOUS, CardSubType.EQUIP, CardSubType.FIELD, CardSubType.COUNTER, CardSubType.EFFECT};
            for (CardSubType subType : sortableSubTypes) {
                if (card.getSubTypes().contains(subType)) {
                    return subType.getCode();
                }
            }
            return 0;
        }
    }
}
