package engine;

import java.util.ArrayList;
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

    //TODO : Remove cards from columns after add to pile
    public static void pushToPile(Card cards, Stack<Card> pile) throws RuleViolationException {

        if(!pile.empty()){
            if (cards.compareTo(cards.getStackRef().peek()) > 0 && pile == cards.getStackRef()) {
                cards.getStackRef().push(cards);
            } else {
                throw new RuleViolationException("Game rules violated.");
            }
        } else{
            if(pile == cards.getStackRef()){
                cards.getStackRef().push(cards);
            } else {
                throw new RuleViolationException("Game rules violated.");
            }
        }


    }

    public static ArrayList<Stack<Card>> getListOfPiles() {
        return listOfPiles;
    }

}
