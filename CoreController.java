import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

/**
 * CoreController is the base code for FreeCell game.
 * It implements all of the logic and rules for the whole game, it also create all the cards,
 * shuffle and also distribute all the cards into all the columns.
 * This class is designed to be implemented in both commandline and GUI version of the game.
 *
 * @author Calvin Lau
 * @version 1.0
 */
public class CoreController {

    private static ArrayList<Card> c1 = new ArrayList<>();
    private static ArrayList<Card> c2 = new ArrayList<>();
    private static ArrayList<Card> c3 = new ArrayList<>();
    private static ArrayList<Card> c4 = new ArrayList<>();
    private static ArrayList<Card> c5 = new ArrayList<>();
    private static ArrayList<Card> c6 = new ArrayList<>();
    private static ArrayList<Card> c7 = new ArrayList<>();
    private static ArrayList<Card> c8 = new ArrayList<>();
    private static ArrayList<Card> c9 = new ArrayList<>();
    private static ArrayList<Card> cardCollections = new ArrayList<>();
    private static HashMap<Card, ArrayList<Card>> positionRec = new HashMap<>();
    private static ArrayList<ArrayList<Card>> listOfColumns = new ArrayList<>();

    CoreController() {

    }

    static {
        initColumnArray();
        createCards();
        Collections.shuffle(c9);
        distributeCards();
    }

    private static void initColumnArray() {
        listOfColumns.add(c1);
        listOfColumns.add(c2);
        listOfColumns.add(c3);
        listOfColumns.add(c4);
        listOfColumns.add(c5);
        listOfColumns.add(c6);
        listOfColumns.add(c7);
        listOfColumns.add(c8);
        listOfColumns.add(c9);
    }

    private static void createCards() {
        String[] cardSuits = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};

        for (int i = 0; i < 4; i++) {

            for (int r = 0; r < cardSuits.length; r++) {
                if (i == 0) {
                    c9.add(new Card(cardSuits[r], r + 1, "c", OrderedStack.getListOfPiles().get(0)));

                } else if (i == 1) {
                    c9.add(new Card(cardSuits[r], r + 1, "d", OrderedStack.getListOfPiles().get(1)));

                } else if (i == 2) {
                    c9.add(new Card(cardSuits[r], r + 1, "h", OrderedStack.getListOfPiles().get(2)));

                } else {
                    c9.add(new Card(cardSuits[r], r + 1, "s", OrderedStack.getListOfPiles().get(3)));

                }
            }

        }

        for (ArrayList<Card> list : listOfColumns) {
            cardCollections.addAll(list);
        }
    }


    private static void distributeCards() {
        for (Card card : c9) {
            if (c9.indexOf(card) <= 6) {
                c1.add(card);
                positionRec.put(card, c1);
            } else if (c9.indexOf(card) <= 13) {
                c2.add(card);
                positionRec.put(card, c2);
            } else if (c9.indexOf(card) <= 20) {
                c3.add(card);
                positionRec.put(card, c3);
            } else if (c9.indexOf(card) <= 27) {
                c4.add(card);
                positionRec.put(card, c4);
            } else if (c9.indexOf(card) <= 33) {
                c5.add(card);
                positionRec.put(card, c5);
            } else if (c9.indexOf(card) <= 39) {
                c6.add(card);
                positionRec.put(card, c6);
            } else if (c9.indexOf(card) <= 45) {
                c7.add(card);
                positionRec.put(card, c7);
            } else if (c9.indexOf(card) <= 51) {
                c8.add(card);
                positionRec.put(card, c8);
            }
        }
        c9.clear();
    }

    /**
     * To move card object from column to column
     *
     * @param card        Card object that needs to be moved
     * @param insertToCol ArrayList referencing the destination column
     * @throws RuleViolationException If the card passed into the column violate the move rules
     */
    public static void moveToColumn(Card card, ArrayList<Card> insertToCol) throws RuleViolationException {

        // if index is not last
        if (positionRec.get(card).indexOf(card) != positionRec.get(card).size() - 1) {
            ArrayList<Card> tempCardList = checkCardOrder(card);
            if (tempCardList != null) {
                // compare last index of insertToCol with 1st index of tempCardList
                if (!insertToCol.isEmpty()) {
                    if (tempCardList.size() != 0 && insertToCol.get(insertToCol.size() - 1).compareTo(tempCardList.get(0)) < 0) {
                        insertToCol.addAll(tempCardList);

                        for (Card listCards : tempCardList) {
                            removeCard(listCards);
                        }

                    } else {
                        tempCardList.clear();
                        throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suit need to be in descending order.");
                    }

                } else {
                    if (tempCardList.size() != 0) {
                        insertToCol.addAll(tempCardList);

                        for (Card listCard : tempCardList) {
                            removeCard(listCard);
                        }

                    }
                }
            } else {
                throw new RuleViolationException("Invalid move.");
            }
        } else {

            if (!insertToCol.isEmpty()) {
                if (insertToCol.get(insertToCol.size() - 1).compareTo(card) < 0) {
                    insertToCol.add(card);
                    removeCard(card);
                } else {
                    throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suits need to be in descending order.");
                }
            } else {
                insertToCol.add(card);
                removeCard(card);
            }
        }

    }

    /**
     * Removes the card object from the source column
     *
     * @param card Card object that needs to be removed
     */
    public static void removeCard(Card card) {
        ArrayList<Card> c;
        c = positionRec.get(card);
        c.remove(card);
        updatePositionRec(card);
    }

    private static void updatePositionRec(Card card) {
        boolean isCardAtPile = true;
        for (ArrayList<Card> list : CoreController.getColumnList()) {
            for (Card searchedCard : list) {
                if (searchedCard == card) {
                    isCardAtPile = false;
                    positionRec.replace(card, list);
                    break;
                }
            }
        }

        if (isCardAtPile) {
            positionRec.remove(card);
        }

    }

    /**
     * To move the card object inside the column.
     *
     * @param column Column that needs to be swapped
     */
    public static void swapCard(ArrayList<Card> column) {
        column.add(0, column.remove(column.size() - 1));
    }

    /**
     * To check start from the index of selected cards object are in descending order until the last index inside a column.
     *
     * @param card Selected card object from a column.
     * @return ArrayList of the card object from the selected index until the last index.
     */
    public static ArrayList<Card> checkCardOrder(Card card) {

        ArrayList<Card> tempCardList = new ArrayList<>();
        // check if order is correct in column before transfer
        for (int i = positionRec.get(card).indexOf(card); i < positionRec.get(card).size(); i++) {
            if (i + 1 != positionRec.get(card).size()) {
                if (positionRec.get(card).get(i).compareTo(positionRec.get(card).get(i + 1)) < 0) {
                    tempCardList.add(positionRec.get(card).get(i));
                } else {
                    //if rules are violated.
                    tempCardList.clear();
                    return null;
                }
            } else {
                tempCardList.add(positionRec.get(card).get(i));
            }
        }

        return tempCardList;
    }

    /**
     * Return all the object of cards
     *
     * @return ArrayList contains all the cards
     */
    public static ArrayList<Card> getCardCollections() {
        return cardCollections;
    }

    /**
     * Return all the column's reference
     *
     * @return ArrayList contains all the references to columns
     */
    public static ArrayList<ArrayList<Card>> getColumnList() {
        return listOfColumns;
    }

    /**
     * Return the card object's current column reference
     *
     * @param card Card object that wants to be searched
     * @return The card object's current column reference
     */
    public static ArrayList<Card> getCardColumn(Card card) {
        return positionRec.get(card);
    }

    /**
     * Check if the game has finished
     *
     * @return true If the game is finished
     */
    public static boolean isGameFinished() {

        return c1.isEmpty() && c2.isEmpty() && c3.isEmpty() && c4.isEmpty() && c5.isEmpty() && c6.isEmpty() && c7.isEmpty() && c8.isEmpty() && c9.isEmpty();
    }

    /**
     * To restart the game
     */
    public static void restartGame() {
        for (ArrayList<Card> columns : getColumnList()) {
            columns.clear();
        }
        for (Stack<Card> pile : OrderedStack.getListOfPiles()) {
            pile.clear();
        }
        positionRec.clear();
        cardCollections.clear();
        createCards();
        Collections.shuffle(c9);
        distributeCards();
    }

}
