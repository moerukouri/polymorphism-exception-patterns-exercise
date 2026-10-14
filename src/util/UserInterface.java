package util;

import exception.ParcelException;
import exception.ServiceNotAllowedException;
import manager.ParcelManager;
import model.*;
import strategy.*;

public class UserInterface {
    private static final InputReader input = new InputReader();
    private static final ParcelManager manager = ParcelManager.getInstance();
    private static final DeliveryStrategy[] SERVICES = {new StandardDelivery(), new ExpressDelivery(), new SameDayDelivery()};
    private static final int BORDER_LENGTH = 50;
    private static final String BORDER_CHAR = "=";
    private static final String BORDER_STRING = BORDER_CHAR.repeat(BORDER_LENGTH);

    public void printMenu() {
        printBanner("Parcel Delivery System");
        System.out.println("1. Add Parcel");
        System.out.println("2. View All Parcel");
        System.out.println("3. Change Delivery Service");
        System.out.println("4. Mark as Delivered");
        System.out.println("5. Exit");
    }

    public int getMenuChoice() {
        return input.readInteger("Enter your choice: ", 1, 5, "Menu choice");
    }

    public void addParcelMenu() {
        printBanner("Add Parcel");
        System.out.println("1. Add Document");
        System.out.println("2. Add Box");
        System.out.println("3. Add Fragile");

        ParcelType type = ParcelType.values()[input.readInteger("Enter parcel type (1-3): ", 1, 3, "Parcel type") - 1];
        
        String trackingNumber = readUniqueTrackingNumber();
        String receiverName = input.readText("Enter receiver name: ", "Receiver name");
        double weight = input.readDouble("Enter weight (kg): ", type.getMaxWeight(), "Weight");

        try{
            Parcel parcel = switch(type) {
                case DOCUMENT -> readDocument(trackingNumber, receiverName, weight);
                case BOX -> readBox(trackingNumber, receiverName, weight);
                case FRAGILE -> readFragile(trackingNumber, receiverName, weight);
            };
            assignService(parcel);
            manager.addParcel(parcel);
            System.out.printf("Parcel added successfully! Delivery fee: Php %.2f%n", parcel.calculateDeliveryFee());
        } catch (ParcelException e) {
            System.out.println("Could not add parcel: " + e.getMessage());
        }
    }

    private Parcel readDocument(String tracking, String receiver, double weight){
        int pages = input.readInteger("Enter number of pages (" + Document.MIN_PAGES + "-" + Document.MAX_PAGES + "): ", Document.MIN_PAGES, Document.MAX_PAGES, "Pages");
        return new Document(tracking, receiver, weight, pages);
    }

    private Parcel readBox(String tracking, String receiver, double weight) {
        int length = readDimension("Length");
        int width = readDimension("Width");
        int height = readDimension("Height");
        return new Box(tracking, receiver, weight, length, width, height);
    }

    private Parcel readFragile(String tracking, String receiver, double weight) {
        int length = readDimension("Length");
        int width = readDimension("Width");
        int height = readDimension("Height");
        String note = input.readText("Enter handling note: ", "Handling note");
        return new Fragile(tracking, receiver, weight, length, width, height, note);
    }

    private int readDimension(String name) {
        return input.readInteger("Enter " + name.toLowerCase() + " (cm): ", 1, Integer.MAX_VALUE, name);
    }

    private String readUniqueTrackingNumber(){
        String trackingNumber = input.readTrackingNumber("Enter tracking number: ");
        while (manager.exists(trackingNumber)) {
            System.out.println("Tracking number " + trackingNumber + " already exists!");
            trackingNumber = input.readTrackingNumber("Enter tracking number: ");
        }
        return trackingNumber;
    }

    private void assignService(Parcel parcel) {
        boolean done = false;
        do {
            try {
                parcel.setDeliveryStrategy(readService());
                done = true;
            } catch (ServiceNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } while (!done);
    }

    private DeliveryStrategy readService() {
        System.out.println("1. Standard  2. Express  3. Same Day");
        return SERVICES[input.readInteger("Select delivery service: ", 1, 3, "Delivery service") - 1];
    }

    public void viewAllParcels(){

    }

    public void changeDeliveryService(){

    }

    public void markAsDelivered(){

    }

    private static void printBanner(String title) {
        System.out.println(BORDER_STRING);
        System.out.println(centerTitle(title));
        System.out.println(BORDER_STRING);
    }

    private static String centerTitle(String title) {
        int padding = (BORDER_LENGTH - title.length()) / 2;
        String centeredTitle = " ".repeat(padding) + title;
        return centeredTitle;
    }
}
