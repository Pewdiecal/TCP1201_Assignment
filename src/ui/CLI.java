package ui;

import engine.Card;
import engine.CoreController;
import engine.OrderedStack;
import engine.RuleViolationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class CLI {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        CLI cli = new CLI();

        String inputRaw;
        String[] commands;

        while (!CoreController.isGameFinished()) {

            cli.printPiles();
            cli.printColumns();
            System.out.println();
            System.out.print("COMMAND > ");

            inputRaw = in.nextLine();
            commands = inputRaw.split(" ");

            if (commands.length == 1) {

                int columnNum;
                try {
                    columnNum = Integer.parseInt(commands[0]);
                } catch (NumberFormatException ex) {

                    if (commands[0].toUpperCase().equals("X")) {
                        System.out.println();
                        System.out.println("Game Closed.");
                        break;
                    } else if (commands[0].toUpperCase().equals("R")) {
                        System.out.println();
                        System.out.println("Restart game.");
                        CoreController.restartGame();
                        cli.pause();
                        continue;
                    } else {
                        System.out.println("Invalid column input.");
                        cli.pause();
                        continue;
                    }

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
                    System.out.println("Invalid column selection.");
                    cli.pause();
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
                            System.out.println("Invalid pile selection.");
                            cli.pause();
                            continue;
                    }

                }

                if (sourceColumn >= 1 && sourceColumn <= 9) {

                    //search card //conversion from user input to card obj
                    for (Card listCard : CoreController.getCardCollections()) {
                        if ((listCard.getPile() + listCard.getSuit()).toUpperCase().equals(commands[1].toUpperCase())) {
                            cardObj = listCard;
                            break;
                        }
                    }

                    //after card found
                    if (cardObj != null) {
                        if (!CoreController.getColumnList().get(sourceColumn - 1).contains(cardObj)) {
                            System.out.println("Card " + commands[1] + " does not exists in source column " + sourceColumn);
                            cli.pause();
                            continue;
                        }

                    } else {
                        System.out.println("Selected card does not exist.");
                        cli.pause();
                        continue;
                    }


                    if (destinationColumn >= 1 && destinationColumn <= 9) {
                        try {
                            CoreController.moveToColumn(cardObj, CoreController.getColumnList().get(destinationColumn - 1));
                        } catch (RuleViolationException e) {
                            System.out.println(e.getMessage());
                            cli.pause();
                        }

                    } else if (destinationPile >= 0) {
                        try {
                            OrderedStack.pushToPile(cardObj, OrderedStack.getListOfPiles().get(destinationPile));
                        } catch (RuleViolationException e) {
                            System.out.println(e.getMessage());
                            cli.pause();
                        }
                    }


                } else {
                    System.out.println("Card not exists.");
                    cli.pause();
                }

            } else {
                System.out.println("Invalid command");
                cli.pause();
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }

        if (CoreController.isGameFinished()) {
            cli.printPiles();
            cli.printColumns();
            System.out.println("CONGRATULATIONS YOU WON!!!");
        }

    }

    public void printColumns() {

        ArrayList<String> tempArray = new ArrayList<>();
        int columnNum = 1;
        for (ArrayList<Card> list : CoreController.getColumnList()) {
            for (Card listCard : list) {
                tempArray.add(listCard.getPile() + listCard.getSuit());
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
                for (Card listCard : pile) {
                    tempArray.add(listCard.getPile() + listCard.getSuit());
                }
                System.out.println("Pile " + pileName + ": " + tempArray);
                tempArray.clear();
            }
        }

    }

    public void pause() {

        System.out.println("\nPRESS ENTER KEY TO CONTINUE...");
        int dump;
        try {
            dump = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
