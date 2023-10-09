package code.app;

import java.util.Scanner;

import code.libMap.Map;
import code.libPlayer.Player;

public class WorldGame {

    Scanner sc = new Scanner(System.in);
    private Player player1;
    private Player player2;
    private Map map;

    public WorldGame() {
        addPlayers();
        createAndPrintMap();
    }

    public void createAndPrintMap() {
        map = new Map(3, 5);
        System.out.println(" START MAP \n" + map.drawMap());
    }

    public void addPlayers() {
        System.out.print(">> Enter your name (playerA): ");
        player1 = new Player(sc.nextLine());

        System.out.print(">> Enter your name (playerB): ");
        player2 = new Player(sc.nextLine());
    }

    // return a valid input as number or character or word in particular case
    public String getInputFrom(String range) { 
        String input = sc.next().toLowerCase();
        while (!input.matches(range)) {
            System.out.print("Invalid Input!. Please choose again: " + range + " : ");
            input = sc.next().toLowerCase();
        }
        return input;
    }

    // "4".matches("[1-4]") return true, "a".matches("a|b|c") return true
    public void chooseAndUpdateTerrain(Player p) {
        System.out.print(">> Please choose Terrain : ");
        int numTerrain = Integer.parseInt(getInputFrom("[1-4]"));
        System.out.println("You have chosen this terrain :");
        p.setChosenTerrain(numTerrain - 1);
        p.getChosenTerrain().printTerrain(); // show the chosen terrain
    }

    public void placeTerrainAndUpdatePoints(Player p) {
        String x, y;
        System.out.print(">> Place in row: ");
        x = getInputFrom("[0-" + (Map.mapHeight - 1) + "]");
        System.out.print(">> Place in column: ");
        y = getInputFrom("[0-" + (Map.mapWidth - 1) + "]");
        if (map.isEmpty(Integer.parseInt(x), Integer.parseInt(y))) {
            p.setPointThisRound(map.pointsEachRound(p.getChosenTerrain(), Integer.parseInt(x), Integer.parseInt(y)));
            p.addPoints(p.getPointThisRound());

        } else {
            System.out.println("This field not available!");
            placeTerrainAndUpdatePoints(p);
        }

    }

    public boolean finishPlacingTerrain(Player p) {
        chooseAndUpdateTerrain(p);
        System.out.print("Do you want to place it? 'y'(yes) / 'n'(no) : ");
        if (getInputFrom("y|yes|n|no").matches("y|yes")) {
            placeTerrainAndUpdatePoints(p);
            System.out.println(map.drawMap());
            return true;
        } else {
            return false;
        }
    }

    public void showPoints(Player p) {
        System.out.println("(Player) " + p.getName() + " got " + p.getPoints() + " points");
    }

    public void showMenuAndMakeChoice(Player p) {
        boolean finishPlacingTerrain = false;
        do {
            System.out.println(
                    "Option: [1]choose and place terrain feld  [2]show points  [3]show map  [4]show tiles  [0]quit");
            System.out.print("You want to : ");
            switch (Integer.parseInt(getInputFrom("[0-4]"))) {
                case 2:
                    showPoints(p);
                    break;
                case 3:
                    System.out.println(map.drawMap());
                    break;
                case 4:
                    p.showTerrainsToChoose();
                    break;
                case 1:
                    finishPlacingTerrain = finishPlacingTerrain(p);
                    break;
                case 0:
                    System.exit(0);
            }
        } while (!finishPlacingTerrain);

    }

    public void playTurn(Player p) {
        System.out.println(" \n >>>>> (Player) " + p.getName() + "\t");
        p.showTerrainsToChoose();
        showMenuAndMakeChoice(p);
        System.out.println("Player " + p.getName() + " has got " + p.getPointThisRound() + " for this round.");
        p.generateDifferentTerrains(); // neune Terrain vom Player werden generiert
    }

    public void gameRunning() {
        int roundCount = 1;

        Player firstPlayer = (int) (Math.random() * 2) == 0 ? player1 : player2;
        Player secondPlayer = firstPlayer.equals(player1) ? player2 : player1;

        while (!map.mapIsFull()) { // Wird immer ausgeführt bis die Karte vollig ausgefüllt wird
            System.out.println("\n\t============= \n\t [ ROUND " + roundCount + " ] \n\t=============\n");

            playTurn(firstPlayer);
            playTurn(secondPlayer);
            if (player1.getPoints() == player2.getPoints()) {
                firstPlayer = secondPlayer;
                secondPlayer = firstPlayer.equals(player1) ? player2 : player1;
            } else {
                firstPlayer = player1.getPoints() > player2.getPoints() ? player1 : player2;
                secondPlayer = firstPlayer.equals(player1) ? player2 : player1;
            }
            roundCount++;
        }
        showResult();
    }

    public void showResult() {
        System.out.println("\n END GAME! Result: ");
        showPoints(player1);
        showPoints(player2);
        if (winner().equals(null)) {
            System.out.println(" * Congratulation, both players won ! *");
        } else {
            System.out.println(" * Congratulation, (Player) " + winner().getName() + " won ! *");
        }
    }

    public Player winner() {
        if (player1.getPoints() == player2.getPoints()) {
            if (player1.getPointThisRound() == player2.getPointThisRound()) {
                return null;
            }
            return player1.getPointThisRound() > player2.getPointThisRound() ? player1 : player2;
        } else {
            return player1.getPoints() > player2.getPoints() ? player1 : player2;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        do {
            WorldGame w = new WorldGame();
            w.gameRunning();
            System.out.println("Do you want to play again? 'y' for yes");
            input = sc.next();
        } while (input.toLowerCase().matches("y|yes"));
        sc.close();
    }
}