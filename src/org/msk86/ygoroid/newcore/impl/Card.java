package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.constant.Attribute;
import org.msk86.ygoroid.newcore.constant.CardSubType;
import org.msk86.ygoroid.newcore.constant.CardType;
import org.msk86.ygoroid.newcore.constant.Race;
import org.msk86.ygoroid.newcore.impl.bmp.CardGenerator;
import org.msk86.ygoroid.newcore.impl.renderer.CardRenderer;

import java.util.List;

public class Card implements Item, Selectable, Controllable, Bmpable {
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
        this.level = level;
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

    public Indicator getIndicator() {
        return indicator;
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
}
