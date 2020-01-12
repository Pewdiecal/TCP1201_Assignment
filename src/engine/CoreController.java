package engine;

import java.util.ArrayList;
import java.util.Collections;

public class CoreController {
    private static ArrayList<Card> pile_c = new ArrayList<>();
    private static ArrayList<Card> pile_d = new ArrayList<>();
    private static ArrayList<Card> pile_h = new ArrayList<>();
    private static ArrayList<Card> pile_s = new ArrayList<>();
    private static ArrayList<Card> c1 = new ArrayList<>();
    private static ArrayList<Card> c2 = new ArrayList<>();
    private static ArrayList<Card> c3 = new ArrayList<>();
    private static ArrayList<Card> c4 = new ArrayList<>();
    private static ArrayList<Card> c5 = new ArrayList<>();
    private static ArrayList<Card> c6 = new ArrayList<>();
    private static ArrayList<Card> c7 = new ArrayList<>();
    private static ArrayList<Card> c8 = new ArrayList<>();
    private static ArrayList<Card> c9 = new ArrayList<>();

    CoreController() {
        createCards();
        Collections.shuffle(pile_c);
        distributeCards();
    }

    private static void createCards() {
        char[] cardSuits = {'A', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X', 'J', 'Q', 'K'};

        for (int i = 0; i < 4; i++) {

            for (int r = 0; r < cardSuits.length; r++) {

                if (i == 0) {
                    pile_c.add(new Card(cardSuits[r], r + 1, 'c'));
                } else if (i == 1) {
                    pile_c.add(new Card(cardSuits[r], r + 1, 'd'));
                } else if (i == 2) {
                    pile_c.add(new Card(cardSuits[r], r + 1, 'h'));
                } else {
                    pile_c.add(new Card(cardSuits[r], r + 1, 's'));
                }
            }

        }
    }

    public static void distributeCards(){
        for(Card cards: pile_c){
            if(pile_c.indexOf(cards) <= 6){
                c1.add(cards);
            } else if(pile_c.indexOf(cards) <= 13){
                c2.add(cards);
            } else if(pile_c.indexOf(cards) <= 20){
                c3.add(cards);
            } else if(pile_c.indexOf(cards) <= 27){
                c4.add(cards);
            } else if(pile_c.indexOf(cards) <= 33){
                c5.add(cards);
            } else if(pile_c.indexOf(cards) <= 39){
                c6.add(cards);
            } else if(pile_c.indexOf(cards) <= 45){
                c7.add(cards);
            } else if(pile_c.indexOf(cards) <= 51){
                c8.add(cards);
            }
        }
        pile_c.clear();
    }

    public static void moveCard(Card card, ArrayList<Card> insertTo){

        ArrayList<Card> c;
        c = searchCard(card);

        if(c != null){
            if(insertTo.get(insertTo.size() - 1).compareTo(card) > 0){
                insertTo.add(card);
                c.remove(card);
            }
        }

    }

    private static ArrayList<Card> searchCard(Card card){
        if(c1.contains(card)){
            return c1;
        }
        if(c2.contains(card)){
            return c2;
        }
        if(c3.contains(card)){
            return c3;
        }
        if(c4.contains(card)){
            return c4;
        }
        if(c5.contains(card)){
            return c5;
        }
        if(c6.contains(card)){
            return c6;
        }
        if(c7.contains(card)){
            return c7;
        }
        if(c8.contains(card)){
            return c8;
        }
        return null;
    }

}
