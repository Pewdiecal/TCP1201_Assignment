package engine;

import java.util.Stack;

public class Card implements Comparable<Card> {

    private String suit;
    private int rank;
    private String pile;
    private Stack<Card> stackRef;

    Card(String suit, int rank, String pile, Stack<Card> stackRef) {
        this.suit = suit;
        this.rank = rank;
        this.pile = pile;
        this.stackRef = stackRef;
    }

    public String getSuit() {
        return this.suit;
    }

    public String getPile() {
        return this.pile;
    }

    public Stack<Card> getStackRef() {
        return this.stackRef;
    }

    @Override
    public int compareTo(Card o) {
        if (this.rank + 1 == o.rank) {
            return 1;
        } else if (this.rank - 1 == o.rank) {
            return -1;
        } else {
            return 0;
        }
    }

}
