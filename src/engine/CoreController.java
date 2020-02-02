package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

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

    public static void distributeCards() {
        for (Card cards : c9) {
            if (c9.indexOf(cards) <= 6) {
                c1.add(cards);
                positionRec.put(cards, c1);
            } else if (c9.indexOf(cards) <= 13) {
                c2.add(cards);
                positionRec.put(cards, c2);
            } else if (c9.indexOf(cards) <= 20) {
                c3.add(cards);
                positionRec.put(cards, c3);
            } else if (c9.indexOf(cards) <= 27) {
                c4.add(cards);
                positionRec.put(cards, c4);
            } else if (c9.indexOf(cards) <= 33) {
                c5.add(cards);
                positionRec.put(cards, c5);
            } else if (c9.indexOf(cards) <= 39) {
                c6.add(cards);
                positionRec.put(cards, c6);
            } else if (c9.indexOf(cards) <= 45) {
                c7.add(cards);
                positionRec.put(cards, c7);
            } else if (c9.indexOf(cards) <= 51) {
                c8.add(cards);
                positionRec.put(cards, c8);
            }
        }
        c9.clear();
    }

    //TODO : auto push to pile if column in order with 1 single pile.
    public static void moveToColumn(Card card, ArrayList<Card> insertTo) throws NullPointerException, RuleViolationException {

        // if index is not last
        if (positionRec.get(card).indexOf(card) != positionRec.get(card).size() - 1) {
            ArrayList<Card> tempCardList = checkCardOrder(card);
            if(tempCardList != null) {
                // compare last index of insertTo with 1st index of tempCardList
                if (!insertTo.isEmpty()) {
                    if (tempCardList.size() != 0 && insertTo.get(insertTo.size() - 1).compareTo(tempCardList.get(0)) < 0) {
                        insertTo.addAll(tempCardList);

                        for (Card listCards : tempCardList) {
                            removeCard(listCards);
                        }

                    } else {
                        tempCardList.clear();
                        throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suit need to be in descending order.");
                    }

                } else {
                    if (tempCardList.size() != 0) {
                        insertTo.addAll(tempCardList);

                        for (Card listCards : tempCardList) {
                            removeCard(listCards);
                        }

                    }
                }
            } else {
                throw new RuleViolationException("Invalid move.");
            }
        } else {

            if (!insertTo.isEmpty()) {
                if (insertTo.get(insertTo.size() - 1).compareTo(card) < 0) {
                    insertTo.add(card);
                    removeCard(card);
                } else {
                    throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suits need to be in descending order.");
                }
            } else {
                insertTo.add(card);
                removeCard(card);
            }
        }

    }

    public static void removeCard(Card card) {
        ArrayList<Card> c;
        c = positionRec.get(card);
        c.remove(card);
        updatePositionRec(card);
    }

    private static void updatePositionRec(Card cards) {
        boolean isCardAtPile = true;
        for (ArrayList<Card> list : CoreController.getColumnList()) {
            for (Card searchedCard : list) {
                if (searchedCard == cards) {
                    isCardAtPile = false;
                    positionRec.replace(cards, list);
                    break;
                }
            }
        }

        if (isCardAtPile) {
            positionRec.remove(cards);
        }

    }

    public static void swapCard(ArrayList<Card> column) {
        column.add(0, column.remove(column.size() - 1));
    }

    public static ArrayList<Card> checkCardOrder(Card card){

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

    public static ArrayList<Card> getCardCollections() {
        return cardCollections;
    }

    public static ArrayList<ArrayList<Card>> getColumnList() {
        return listOfColumns;
    }

    public static ArrayList<Card> getCardColumn(Card card){
        return positionRec.get(card);
    }

    public static boolean isGameFinished() {

        return c1.isEmpty() && c2.isEmpty() && c3.isEmpty() && c4.isEmpty() && c5.isEmpty() && c6.isEmpty() && c7.isEmpty() && c8.isEmpty() && c9.isEmpty();
    }

    public static void restartGame(){
        for(ArrayList<Card> columns:getColumnList()){
            columns.clear();
        }
        for(Stack<Card> pile:OrderedStack.getListOfPiles()){
            pile.clear();
        }
        positionRec.clear();
        cardCollections.clear();
        createCards();
        Collections.shuffle(c9);
        distributeCards();
    }

    public static void autoSolve(){
        
    }

}
