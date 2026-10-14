package app;

import util.UserInterface;

public class Main {
    private static final UserInterface ui = new UserInterface();

    public static void main(String[] args) {
        boolean running = true;
        do { 
            ui.printMenu();
            int choice = ui.getMenuChoice();
            switch (choice) {
                case 1 -> ui.addParcelMenu();
                case 2 -> ui.viewAllParcels();
                case 3 -> ui.changeDeliveryService();
                case 4 -> ui.markAsDelivered();
                case 5 -> {
                    System.out.println("Exiting the program.");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (running);
    }
}
