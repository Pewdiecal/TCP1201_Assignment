import java.util.Stack;

/**
 * Card is a class that defines the characteristic of a poker card.
 *
 * @author Calvin Lau
 * @version 1.0
 */
public class Card implements Comparable<Card> {

    private String suit;
    private int rank;
    private String pile;
    private Stack<Card> stackRef;

    /**
     * To initialize the properties of a poker card.
     *
     * @param suit     Card suit
     * @param rank     Card ranking
     * @param pile     A pile that a card belongs to
     * @param stackRef A reference to the stack which the will be stored on
     */
    Card(String suit, int rank, String pile, Stack<Card> stackRef) {
        this.suit = suit;
        this.rank = rank;
        this.pile = pile;
        this.stackRef = stackRef;
    }

    /**
     * Return the suit of a card
     *
     * @return String suit of a card
     */
    public String getSuit() {
        return this.suit;
    }

    /**
     * Return the pile of a card
     *
     * @return String pile of a card
     */
    public String getPile() {
        return this.pile;
    }

    /**
     * Return the rank of a card
     *
     * @return int rank of a card
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Return the stack reference to a stack of a card
     *
     * @return Stack reference of a card which the card will be stored on later
     */
    public Stack<Card> getStackRef() {
        return this.stackRef;
    }

    /**
     * Compare if the card is in ascending or descending order.
     *
     * @return the value 1 if this Card rank is in ascending order; a value less than -1 if this Card rank is in descending order.
     */
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
