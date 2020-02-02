import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * OrderedStack is a class that handles the rules of cards being pushed into a stack.
 *
 * @author Calvin Lau
 * @version 1.0
 */
public class OrderedStack {

    private static Stack<Card> pile_c = new Stack<>();
    private static Stack<Card> pile_d = new Stack<>();
    private static Stack<Card> pile_h = new Stack<>();
    private static Stack<Card> pile_s = new Stack<>();
    private static ArrayList<Stack<Card>> listOfPiles = new ArrayList<>();

    OrderedStack() {

    }

    static {
        initPiles();
    }

    private static void initPiles() {
        listOfPiles.add(pile_c);
        listOfPiles.add(pile_d);
        listOfPiles.add(pile_h);
        listOfPiles.add(pile_s);
    }

    /**
     * Push card object to a pile from a column.
     *
     * @param card   Card object that want to be pushed into a pile
     * @param toPile Reference to a pile destination
     * @throws RuleViolationException If the move violates the rule
     */
    public static void pushToPile(Card card, Stack<Card> toPile) throws RuleViolationException {

        if (CoreController.getCardColumn(card).indexOf(card) != CoreController.getCardColumn(card).size() - 1) {
            ArrayList<Card> tempCardList = CoreController.checkCardOrder(card);

            if (tempCardList != null) {

                for (Card listCard : tempCardList) {
                    if (listCard.getStackRef() != toPile) {
                        throw new RuleViolationException("Selected cards does not match its own specific pile.");
                    }
                }

                tempCardList.sort(Collections.reverseOrder());

                if (!toPile.empty()) {

                    if (toPile.peek().compareTo(tempCardList.get(0)) > 0) {

                        for (Card listCard : tempCardList) {
                            listCard.getStackRef().push(listCard);
                            CoreController.removeCard(listCard);
                        }

                    } else {
                        throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suits need to be in ascending order.");
                    }

                } else {

                    if (tempCardList.get(0).getRank() == 1) {

                        for (Card listCard : tempCardList) {
                            listCard.getStackRef().push(listCard);
                            CoreController.removeCard(listCard);
                        }

                    } else {
                        throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: The first card needs to have suit A.");
                    }

                }


            } else {
                throw new RuleViolationException("Invalid move.");
            }

        } else {

            if (!toPile.empty()) {

                if (toPile.peek().compareTo(card) > 0 && toPile == card.getStackRef()) {
                    card.getStackRef().push(card);
                    CoreController.removeCard(card);

                } else {
                    throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suits need to be in ascending order.");
                }

            } else {

                if (toPile == card.getStackRef() && card.getRank() == 1) {
                    card.getStackRef().push(card);
                    CoreController.removeCard(card);

                } else {
                    throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: The first card needs to start with suit A.");
                }

            }

        }

    }

    /**
     * Return all the references of stacks.
     *
     * @return ArrayList contains all the references of stacks
     */
    public static ArrayList<Stack<Card>> getListOfPiles() {
        return listOfPiles;
    }

}
