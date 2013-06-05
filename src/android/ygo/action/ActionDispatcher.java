package android.ygo.action;

import android.ygo.core.*;
import android.ygo.core.tool.Coin;
import android.ygo.core.tool.Dice;
import android.ygo.op.*;

public class ActionDispatcher {

    public static Action dispatch(Click click) {
        Action action = new SelectAction(click);
        if(click.getItem() instanceof LifePoint) {
            action = new LifePointAction(click);
        }
        if(click.getItem() instanceof Dice) {
            action = new DiceAction(click);
        }
        if(click.getItem() instanceof Coin) {
            action = new CoinAction(click);
        }
        if (click.getContainer() instanceof InfoWindow) {
            if (click.getItem() instanceof Card || click.getItem() instanceof OverRay)
                action = new OpenInfoAction(click);
        }
        return action;
    }

    public static Action dispatch(Press press) {
        Action action = new SelectAction(press);
        if (press.getContainer() instanceof Field) {
            Field field = (Field) press.getContainer();
            if (field.getType() == FieldType.MONSTER_ZONE) {
                SelectableItem item = press.getItem();
                if (item instanceof Card || item instanceof OverRay) {
                    action = new MonsterPositionAction(press);
                }
            }
        }
        return action;
    }

    public static Action dispatch(DoubleClick dblClick) {
        Action action = new EmptyAction();
        SelectableItem item = dblClick.getItem();
        Item container = dblClick.getContainer();
        if (container instanceof Field) {
            Field field = (Field) container;
            if (item == null) {
                if (field.getType() == FieldType.MONSTER_ZONE) {
                    action = new NewTokenAction(dblClick);
                }
            } else {
                if (item instanceof Card || item instanceof OverRay) {
                    action = new FlipAction(dblClick);
                } else if (item instanceof CardList) {
                    CardList cardList = (CardList) item;
                    if (cardList.getName().equals("TEMPORARY") || cardList.getName().equals("DECK")) {
                        action = new ShuffleAction(dblClick);
                    }
                }
            }
        } else if(container instanceof CardList) {
            CardList cardList = (CardList) container;
            if(cardList.getName().equals("TEMPORARY")) {
                action = new FlipAction(dblClick);
            }
        } else if(container instanceof HandCards) {
            Card card = (Card)item;
            if(card != null) {
                action = new FlipAction(dblClick);
            }
        }
        return action;
    }

    public static Action dispatch(StartDrag startDrag) {
        Action action = new EmptyAction();
        if (startDrag.getContainer() instanceof HandCards) {
            action = new DragHandCardAction(startDrag);
        } else if (startDrag.getContainer() instanceof Field) {
            SelectableItem item = startDrag.getItem();
            if (item != null) {
                if (item instanceof OverRay) {
                    action = new DragOverRayAction(startDrag);
                } else if (item instanceof Card) {
                    action = new DragFieldCardAction(startDrag);
                } else if (item instanceof CardList) {
                    action = new DragCardListAction(startDrag);
                }
            }
        } else if (startDrag.getContainer() instanceof CardList) {
            action = new DragCardSelectorAction(startDrag);
        }
        return action;
    }

    public static Action dispatch(Drag drag) {
        Action action = new RevertDragAction(drag);
        if (drag.getContainer() instanceof Field) {
            Field field = (Field) drag.getContainer();
            SelectableItem targetItem = field.getItem();
            if (targetItem == null) {
                if (drag.getStartDrag().getContainer() instanceof HandCards) {
                    Card card = (Card) drag.getItem();
                    if(card != null && card.isOpen()) {
                        action = new SummonOrEffectAction(drag);
                    } else {
                        action = new SetAction(drag);
                    }
                } else {
                    action = new MoveAction(drag);
                }
            }
            if (drag.getItem() instanceof Card && (targetItem instanceof Card || targetItem instanceof OverRay)) {
                action = new OverRayAction(drag);
            }
            if ((drag.getItem() instanceof Card || drag.getItem() instanceof OverRay) && targetItem instanceof CardList) {
                action = new AddCardListAction(drag);
            }
        }
        if (drag.getContainer() instanceof HandCards) {
            if (drag.getItem() instanceof Card) {
                action = new AddHandCardAction(drag);
            }
        }
        return action;
    }

    public static Action dispatch(ReturnClick click) {
        Action action = new EmptyAction();
        if (click.getDuel().getCardSelector() != null) {
            action = new CloseCardSelectorAction(click);
        } else {
            SelectableItem item = click.getItem();
            if (item != null) {
                if (item instanceof CardList || item instanceof OverRay) {
                    action = new OpenCardSelectorAction(click);
                }
            }
        }
        return action;
    }

    public static Action dispatch(MenuClick menuClick) {
        Action action = new EmptyAction();
        switch (menuClick.getMenuItem().getGroupId()) {
            case Const.MENU_GROUP_DECK :
                switch (menuClick.getMenuItem().getItemId()) {
                    case Const.MENU_DECK_SHUFFLE :
                        action = new ShuffleAction(menuClick);
                        break;
                    case Const.MENU_DECK_CLOSE_REMOVE_TOP :
                        action = new CloseRemoveTopAction(menuClick);
                        break;
                    case Const.MENU_DECK_REVERSE :
                        action = new ReserveDeckAction(menuClick);
                        break;
                    case Const.MENU_DECK_RESTART :
                        action = new RestartAction(menuClick);
                        break;
                    case Const.MENU_DECK_CHANGE_DECK :
                        action = new DeckChangeAction(menuClick);
                        break;
                    case Const.MENU_MIRROR_DISPLAY :
                        action = new MirrorDisplayAction(menuClick);
                        break;
                }
                break;
            case Const.MENU_GROUP_CARD :
                switch (menuClick.getMenuItem().getItemId()) {
                    case Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK :
                        break;
                    case Const.MENU_CARD_CLOSE_REMOVE :
                        action = new CloseRemoveCardAction(menuClick);
                        break;
                }
                break;

        }
        return action;
    }
}
