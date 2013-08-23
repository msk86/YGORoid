package android.ygo.action;

import android.ygo.R;
import android.ygo.core.*;
import android.ygo.core.tool.Coin;
import android.ygo.core.tool.Dice;
import android.ygo.layout.Layout;
import android.ygo.op.*;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

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
        if (click.getDuel().getSideWindow() != null && item instanceof Card && click.getContainer() instanceof Layout) {
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
        return action;
    }

    public static Action dispatch(DoubleClick dblClick) {
        Action action = new EmptyAction();
        SelectableItem item = dblClick.getItem();
        Item container = dblClick.getContainer();
        Duel duel = dblClick.getDuel();
        if (duel.isDuelDisk()) {
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
                        action = new OpenCardSelectorAction(dblClick);
                    } else if (item instanceof InfoWindow) {
                        if(field.getItem() instanceof OverRay || field.getItem() instanceof CardList)
                        action = new OpenCardSelectorAction(dblClick);
                    }
                }
            } else if (container instanceof HandCards && item instanceof Card) {
                action = new FlipAction(dblClick);
            }
        }

        if (duel.isCardSelector()) {
            CardSelector cardSelector = duel.getCardSelector();
            if (item instanceof Card) {
                if (cardSelector.getCardList().getName().equals(Utils.s(R.string.TEMPORARY))) {
                    action = new FlipAction(dblClick);
                } else {
                    action = new CardSelectorToTempAction(dblClick);
                }
            }
        }

        if (duel.isSideWindow() && item instanceof Card && container instanceof Layout) {
            action = new ChangeSideAction(dblClick);
        }
        return action;
    }

    public static Action dispatch(StartDrag startDrag) {
        Action action = new EmptyAction();
        Item container = startDrag.getContainer();
        SelectableItem item = startDrag.getItem();
        if (container instanceof HandCards && item instanceof Card) {
            action = new DragHandCardAction(startDrag);
        }
        if (container instanceof Field) {
            if (item instanceof OverRay) {
                action = new DragOverRayAction(startDrag);
            } else if (item instanceof Card) {
                if (startDrag.getDuel().getCardSelector() == null) {
                    action = new DragFieldCardAction(startDrag);
                } else {
                    action = new DragCardSelectorAction(startDrag);
                }
            } else if (item instanceof CardList) {
                action = new DragCardListAction(startDrag);
            }
        }
        return action;
    }

    public static Action dispatch(Drag drag) {
        if (drag.getItem() == null) {
            if (drag.getStartDrag().getItem() instanceof InfoWindow) {
                return new OpenMenuAction(drag);
            }
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
        if (click.getDuel().getCardWindow() != null) {
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
        }
        return action;
    }

    public static Action dispatch(MenuClick menuClick) {
        Action action = new EmptyAction();
        switch (menuClick.getMenuItem().getGroupId()) {
            case Const.MENU_GROUP_MAIN:
                switch (menuClick.getMenuItem().getItemId()) {
                    case Const.MENU_RESTART:
                        action = new RestartAction(menuClick);
                        break;
                    case Const.MENU_CHANGE_DECK:
                        action = new DeckChangeAction(menuClick);
                        break;
                    case Const.MENU_SIDE:
                        action = new OpenSideWindowAction(menuClick);
                        break;
                    case Const.MENU_CARD_SEARCH:
                        action = new CardSearchAction(menuClick);
                        break;
                    case Const.MENU_DECK_BUILDER:
                        action = new DeckBuilderAction(menuClick);
                        break;
                    case Const.MENU_GRAVITY_TOGGLE:
                        action = new ToggleAction(menuClick, Configuration.PROPERTY_GRAVITY_ENABLE);
                        break;
                    case Const.MENU_FPS_TOGGLE:
                        action = new ToggleAction(menuClick, Configuration.PROPERTY_FPS_ENABLE);
                        break;
                    case Const.MENU_AUTO_SHUFFLE_TOGGLE:
                        action = new ToggleAction(menuClick, Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE);
                        break;
                    case Const.MENU_MIRROR_DISPLAY:
                        action = new MirrorDisplayAction(menuClick);
                        break;
                    case Const.MENU_FEEDBACK:
                        action = new FeedbackAction(menuClick);
                        break;
                    case Const.MENU_EXIT:
                        action = new ExitConfirmAction(menuClick);
                        break;
                }
                break;
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
