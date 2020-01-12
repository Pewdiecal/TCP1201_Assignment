package engine;

public class Card implements Comparable<Card>{

    private char suit;
    private int rank;
    private char pile;

    Card(char suit, int rank, char pile){
        this.suit = suit;
        this.rank = rank;
        this.pile = pile;
    }

    public char getSuit(){
        return this.suit;
    }

    public int getRank(){
        return this.rank;
    }

    public char getPile(){
        return this.pile;
    }

    @Override
    public int compareTo(Card o) {

        return 0;
    }

}
