package android.ygo.views.deckbuilder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.util.TypedValue;
import android.widget.TextView;
import android.ygo.R;
import android.ygo.core.Card;

public class CardNameView extends TextView {
    private Card card;

    public CardNameView(Context context) {
        super(context);
        this.setSingleLine();
        this.setPadding(3, 3, 3, 3);
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        XmlResourceParser xrp = getResources().getXml(R.color.card_name_view);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            this.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    public CardNameView(Context context, Card card) {
        this(context);
        this.card = card;
        this.setText(card.getName());
    }

    public Card getCard() {
        return card;
    }
}
