import java.util.InputMismatchException;
import java.util.Scanner;

public class BattleshipGame {
    private Ocean ocean;
    private final Scanner scanner;

    public BattleshipGame() {
        ocean = new Ocean();
        scanner = new Scanner(System.in);
    }

    private void setupGame() {
        // Initialize the game board and place ships
        ocean.placeAllShipsRandomly();
        System.out.println("Welcome to Battleship!");
    }

    private void takeTurn() {
        boolean validInput = false;
        int row = -1;
        int column = -1;

        while (!validInput) {
            try {
                System.out.println("Enter row and column number (e.g., 5 7): ");
                row = scanner.nextInt();
                column = scanner.nextInt();
                if (row < 0 || row > 9 || column < 0 || column > 9) {
                    System.out.println("Invalid row or column number. Try again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("That's not an integer. Please enter valid integers for row and column.");
                scanner.nextLine(); // This clears the buffer and resets the scanner
            }
        }

        boolean hit = ocean.shootAt(row, column);
        if (hit) {
            System.out.println("Hit!");
            if (ocean.getShipArray()[row][column].isSunk()) {
                System.out.println("You just sunk a " + ocean.getShipArray()[row][column].getShipType());
            }
        } else {
            System.out.println("Miss.");
        }
    }


    public void play() {
        setupGame();
        while (!ocean.areAllShipsSunk()) {
            takeTurn();
            updateDisplay();
        }
        printFinalScore();
        askForReplay();
    }

    private void updateDisplay() {
        ocean.print();
    }

    private void printFinalScore() {
        System.out.println("Game Over! You took " + ocean.getShotsFired() + " shots.");
    }

    private void askForReplay() {
        System.out.println("Do you want to play again? (yes/no): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("yes")) {
            ocean = new Ocean(); // Reset the game
            play();
        } else {
            System.out.println("Thank you for playing Battleship!");
        }
    }

    public static void main(String[] args) {
        BattleshipGame game = new BattleshipGame();
        game.play();
    }
}
