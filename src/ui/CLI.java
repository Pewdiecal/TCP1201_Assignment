package ui;

import engine.Card;
import engine.CoreController;
import engine.OrderedStack;
import engine.RuleViolationException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class CLI {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        CLI cli = new CLI();

        while (!CoreController.isGameFinished()) {

            cli.printPiles();
            cli.printColumns();

            String inputRaw = in.nextLine();
            String[] commands = inputRaw.split(" ");

            if (commands.length == 1) {

                int columnNum = 0;
                try {
                    columnNum = Integer.parseInt(commands[0]);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid column input.");
                }
                CoreController.swapCard(CoreController.getColumnList().get(columnNum - 1));

            } else if (commands.length == 3) {
                int sourceColumn;
                int destinationColumn = -1;
                int destinationPile = -1;
                Card cardObj = null;

                try {
                    sourceColumn = Integer.parseInt(commands[0]);
                } catch (NumberFormatException ex) {
                    System.out.println("Column not exists.");
                    continue;
                }

                try {
                    destinationColumn = Integer.parseInt(commands[2]);
                } catch (NumberFormatException ex) {

                    switch (commands[2].toUpperCase()) {
                        case "C":
                            destinationPile = 0;
                            break;
                        case "D":
                            destinationPile = 1;
                            break;
                        case "H":
                            destinationPile = 2;
                            break;
                        case "S":
                            destinationPile = 3;
                            break;
                        default:
                            System.out.println("Invalid pile destination.");
                            continue;
                    }

                }

                if (sourceColumn >= 1 && sourceColumn <= 9) {
                    //search card //conversion from user input to card obj
                    for (Card cards : CoreController.getCardCollections()) {
                        if ((cards.getPile() + cards.getSuit()).toUpperCase().equals(commands[1].toUpperCase())) {
                            cardObj = cards;
                            break;
                        }
                    }

                    //after card found
                    if (cardObj != null) {
                        if (!CoreController.getColumnList().get(sourceColumn - 1).contains(cardObj)) {
                            System.out.println("Card not exists in source column " + sourceColumn);
                            continue;
                        }

                    } else {
                        System.out.println("Card not exists.");
                        continue;
                    }


                    if (destinationColumn >= 1 && destinationColumn <= 9) {
                        try {
                            CoreController.moveToColumn(cardObj, CoreController.getColumnList().get(destinationColumn - 1));
                        } catch (RuleViolationException e) {
                            System.out.println(e.getMessage());
                        }

                    } else if (destinationPile >= 0) {
                        try {
                            OrderedStack.pushToPile(cardObj, OrderedStack.getListOfPiles().get(destinationPile));
                        } catch (RuleViolationException e) {
                            System.out.println(e.getMessage());
                        }
                    }


                } else {
                    System.out.println("Card not exists.");
                }

            } else {
                System.out.println("Invalid command");
            }

        }

    }

    public void printColumns() {

        ArrayList<String> tempArray = new ArrayList<>();
        int columnNum = 1;
        for (ArrayList<Card> list : CoreController.getColumnList()) {
            for (Card cards : list) {
                tempArray.add(cards.getPile() + cards.getSuit());
            }
            System.out.println("Column " + columnNum + ": " + tempArray);
            columnNum++;
            tempArray.clear();
        }

    }

    public void printPiles() {

        ArrayList<String> tempArray = new ArrayList<>();
        char pileName = 'a';

        for (Stack<Card> pile : OrderedStack.getListOfPiles()) {

            if (OrderedStack.getListOfPiles().get(0) == pile) {
                pileName = 'c';
            } else if (OrderedStack.getListOfPiles().get(1) == pile) {
                pileName = 'd';
            } else if (OrderedStack.getListOfPiles().get(2) == pile) {
                pileName = 'h';
            } else if (OrderedStack.getListOfPiles().get(3) == pile) {
                pileName = 's';
            }

            if (pile.isEmpty()) {
                System.out.println("Pile " + pileName + ": []");
            } else {
                for (Card cards : pile) {
                    tempArray.add(cards.getPile() + cards.getSuit());
                }
                System.out.println("Pile " + pileName + ": " + tempArray);
                tempArray.clear();
            }
        }

    }

}
