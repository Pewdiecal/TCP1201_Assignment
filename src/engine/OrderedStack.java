package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class OrderedStack {

    private static Stack<Card> pile_c = new Stack<>();
    private static Stack<Card> pile_d = new Stack<>();
    private static Stack<Card> pile_h = new Stack<>();
    private static Stack<Card> pile_s = new Stack<>();
    private static ArrayList<Stack<Card>> listOfPiles = new ArrayList<>();

    static {
        initPiles();
    }

    private static void initPiles() {
        listOfPiles.add(pile_c);
        listOfPiles.add(pile_d);
        listOfPiles.add(pile_h);
        listOfPiles.add(pile_s);
    }


    public static void pushToPile(Card cards, Stack<Card> pile) throws RuleViolationException {

        if (CoreController.getCardColumn(cards).indexOf(cards) != CoreController.getCardColumn(cards).size() - 1) {
            ArrayList<Card> tempCardList = CoreController.checkCardOrder(cards);

            if (tempCardList != null) {

                for(Card card:tempCardList){
                    if(card.getStackRef() != pile){
                        throw new RuleViolationException("Selected cards does not match its own specific pile.");
                    }
                }

                tempCardList.sort(Collections.reverseOrder());
                if(!pile.empty()){

                    if (pile.peek().compareTo(tempCardList.get(0)) > 0) {
                        for(Card card:tempCardList){
                            cards.getStackRef().push(card);
                            CoreController.removeCard(card);
                        }
                    } else {
                        throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suits need to be in ascending order.");
                    }

                } else {
                    if (tempCardList.get(0).getRank() == 1) {
                        for(Card card:tempCardList){
                            cards.getStackRef().push(card);
                            CoreController.removeCard(card);
                        }

                    } else {
                        throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: The first card needs to have suit A.");
                    }
                }


            } else {
                throw new RuleViolationException("Invalid move.");
            }

        } else {

            if (!pile.empty()) {
                if (pile.peek().compareTo(cards) > 0 && pile == cards.getStackRef()) {
                    cards.getStackRef().push(cards);

                    CoreController.removeCard(cards);

                } else {
                    throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: Card suits need to be in ascending order.");
                }
            } else {

                if (pile == cards.getStackRef() && cards.getRank() == 1) {
                    cards.getStackRef().push(cards);

                    CoreController.removeCard(cards);

                } else {
                    throw new RuleViolationException("Card arrangement does not follow the rule.\nNOTE: The first card needs to start with suit A.");
                }
            }

        }

    }

    public static ArrayList<Stack<Card>> getListOfPiles() {
        return listOfPiles;
    }

}
