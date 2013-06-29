package android.ygo.action;

import android.ygo.core.*;
import android.ygo.core.tool.Coin;
import android.ygo.core.tool.Dice;
import android.ygo.layout.Layout;
import android.ygo.op.*;

public class ActionDispatcher {

    public static Action dispatch(Click click) {
        Action action = new SelectAction(click);
        SelectableItem item = click.getItem();
        if (item instanceof LifePoint) {
            action = new LifePointAction(click);
        }
        if (item instanceof Dice) {
            action = new DiceAction(click);
        }
        if (item instanceof Coin) {
            action = new CoinAction(click);
        }
        if(click.getDuel().getSideWindow() != null && item instanceof Card && click.getContainer() instanceof Layout) {
            action = new SelectSideAction(click);
        }
        if (item instanceof InfoWindow) {
            InfoWindow infoWindow = (InfoWindow) item;
            if (infoWindow.getInfoItem() instanceof Card || infoWindow.getInfoItem() instanceof OverRay)
                action = new OpenCardWindowAction(click);
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
        if(press.getDuel().getSideWindow() != null) {
            action = new SideWindowFlipAction(press);
        }
//        if (press.getItem() == null && press.getContainer() == null) {
//            action = new ScreenShotAction(press);
//        }
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
                    SelectableItem fieldItem = field.getItem();
                    if(fieldItem instanceof CardList) {
                        CardList cardList = (CardList) fieldItem;
                        if (cardList.getName().equals(CardList.TEMPORARY)) {
                            action = new FlipAction(dblClick);
                        }
                    } else {
                        action = new FlipAction(dblClick);
                    }
                } else if (item instanceof CardList) {
                    action = new OpenCardSelectorAction(dblClick);
                }
            }
        } else if (container instanceof HandCards) {
            Card card = (Card) item;
            if (card != null) {
                action = new FlipAction(dblClick);
            }
        }

        if(dblClick.getDuel().getSideWindow() != null && item instanceof Card && container instanceof Layout) {
            action = new ChangeSideAction(dblClick);
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
                    if(startDrag.getDuel().getCardSelector() == null) {
                        action = new DragFieldCardAction(startDrag);
                    } else {
                        action = new DragCardSelectorAction(startDrag);
                    }
                } else if (item instanceof CardList) {
                    action = new DragCardListAction(startDrag);
                }
            }
        }
        return action;
    }

    public static Action dispatch(Drag drag) {
        if (drag.getItem() == null) {
            return new EmptyAction();
        }
        Action action = new RevertDragAction(drag);
        if (drag.getContainer() instanceof Field) {
            Field field = (Field) drag.getContainer();
            SelectableItem targetItem = field.getItem();
            if (targetItem == null) {
                if (drag.getStartDrag().getContainer() instanceof HandCards) {
                    Card card = (Card) drag.getItem();
                    if (card.isOpen()) {
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
        if(click.getDuel().getCardWindow() != null) {
            action = new CloseCardWindowAction(click);
        } else if (click.getDuel().getCardSelector() != null) {
            action = new CloseCardSelectorAction(click);
        } else if (click.getDuel().getSideWindow() != null) {
            action = new CloseSideWindowAction(click);
        } else {
            SelectableItem item = click.getItem();
            if (item != null) {
                if (item instanceof CardList || item instanceof OverRay) {
                    action = new OpenCardSelectorAction(click);
                }
            }
            else {
            	action = new ExitConfirmAction(click);
            }
        }
        return action;
    }

    public static Action dispatch(MenuClick menuClick) {
        Action action = new EmptyAction();
        switch (menuClick.getMenuItem().getGroupId()) {
            case Const.MENU_GROUP_DECK:
                switch (menuClick.getMenuItem().getItemId()) {
                    case Const.MENU_DECK_SHUFFLE:
                        action = new ShuffleAction(menuClick);
                        break;
                    case Const.MENU_DECK_CLOSE_REMOVE_TOP:
                        action = new CloseRemoveTopAction(menuClick);
                        break;
                    case Const.MENU_DECK_REVERSE:
                        action = new ReserveDeckAction(menuClick);
                        break;
                    case Const.MENU_DECK_RESTART:
                        action = new RestartAction(menuClick);
                        break;
                    case Const.MENU_DECK_CHANGE_DECK:
                        action = new DeckChangeAction(menuClick);
                        break;
                    case Const.MENU_DECK_SIDE:
                        action = new OpenSideWindowAction(menuClick);
                        break;
                    case Const.MENU_MIRROR_DISPLAY:
                        action = new MirrorDisplayAction(menuClick);
                        break;
                    case Const.MENU_EXIT:
                        action = new ExitConfirmAction(menuClick);
                        break;
                }
                break;
            case Const.MENU_GROUP_FIELD_CARD:
                switch (menuClick.getMenuItem().getItemId()) {
                    case Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK:
                        action = new ToDeckBottomAction(menuClick);
                        break;
                    case Const.MENU_CARD_CLOSE_REMOVE:
                        action = new CloseRemoveCardAction(menuClick);
                        break;
                }
                break;
            case Const.MENU_GROUP_HAND_CARD:
                switch (menuClick.getMenuItem().getItemId()) {
                    case Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK:
                        action = new ToDeckBottomAction(menuClick);
                        break;
                    case Const.MENU_CARD_CLOSE_REMOVE:
                        action = new CloseRemoveCardAction(menuClick);
                        break;
                    case Const.MENU_SHOW_HAND:
                        action = new ShowHandCardAction(menuClick);
                        break;
                    case Const.MENU_HIDE_HAND:
                        action = new HideHandCardAction(menuClick);
                        break;
                    case Const.MENU_SHUFFLE_HAND:
                        action = new ShuffleHandCardAction(menuClick);
                        break;
                }
                break;

        }
        return action;
    }

    public static Action dispatch(VolClick volClick) {
        Action action = new EmptyAction();
        if (volClick.getItem() == null) {
            return action;
        }
        Item container = volClick.getContainer();
        if (container instanceof Field) {
            Field field = (Field) container;
            if (field.getType() == FieldType.MONSTER_ZONE ||
                    field.getType() == FieldType.MAGIC_ZONE ||
                    field.getType() == FieldType.FIELD_MAGIC_ZONE) {
                action = new IndicatorAction(volClick);
            }
        }
        return action;
    }
}
