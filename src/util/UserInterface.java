package util;

import exception.ParcelException;
import exception.ParcelNotFoundException;
import exception.ServiceNotAllowedException;
import java.util.Collection;
import manager.ParcelManager;
import model.*;
import strategy.*;

public class UserInterface {
    private static final InputReader input = new InputReader();
    private static final ParcelManager manager = ParcelManager.getInstance();
    private static final DeliveryStrategy[] SERVICES = {new StandardDelivery(), new ExpressDelivery(), new SameDayDelivery()};
    private static final int MAX_DIMENSIONS = 200;
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

    // --- 1. Add Parcel ---
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
            System.out.printf("Parcel added successfully! Delivery fee: Php %,.2f%n", parcel.calculateDeliveryFee());
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
        return input.readInteger("Enter " + name.toLowerCase() + " (cm): ", 1, MAX_DIMENSIONS, name);
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
        printBanner("Delivery Service");
        System.out.println("1. Standard");
        System.out.println("2. Express");
        System.out.println("3. Same Day");
        return SERVICES[input.readInteger("Select delivery service: ", 1, 3, "Delivery service") - 1];
    }
    
    // --- 2. View All Parcels ---
    public void viewAllParcels(){
        printBanner("All Parcels");
        if (!hasParcels()){
            return;
        }
        printParcels(manager.getAllParcels());
    }

    private static void printParcels(Collection<Parcel> parcels){
        int trkNumWidth = "Tracking Number".length();
        int typeWidth = "Parcel Type".length();
        int nameWidth = "Receiver Name".length();
        int chargeWeightWidth = "Chargeable Weight (kg)".length();
        int serviceWidth = "Delivery Service".length();
        int feeWidth = "Delivery Fee".length();
        int statusWidth = "Status".length();

        for (Parcel p : parcels){
            trkNumWidth = Math.max(trkNumWidth, p.getTrackingNumber().length());
            typeWidth = Math.max(typeWidth, p.getParcelType().getLabel().length());
            nameWidth = Math.max(nameWidth, p.getReceiverName().length());
            chargeWeightWidth = Math.max(chargeWeightWidth, String.format("%,.3f", p.getChargeableWeight()).length());
            serviceWidth = Math.max(serviceWidth, p.getDeliveryStrategy().getServiceName().length());
            feeWidth = Math.max(feeWidth, String.format("Php %,.2f", p.calculateDeliveryFee()).length());
            statusWidth = Math.max(statusWidth, p.getStatus().length());
        }

        printTableHeader(trkNumWidth, typeWidth, nameWidth, chargeWeightWidth, serviceWidth, feeWidth, statusWidth);
        for (Parcel p : parcels){
            System.out.println(p.toTableRow(trkNumWidth, typeWidth, nameWidth, chargeWeightWidth, serviceWidth, feeWidth, statusWidth));
        }
    }

    private static void printTableHeader(int trkNumWidth, int typeWidth, int nameWidth, int chargeWeightWidth, int serviceWidth, int feeWidth, int statusWidth){
        int borderWidth = trkNumWidth + typeWidth + nameWidth + chargeWeightWidth + serviceWidth + feeWidth + statusWidth + 19;
        String format = " %-" + trkNumWidth + "s | %-" + typeWidth + "s | %-" + nameWidth + "s | %-" + chargeWeightWidth + "s | %-" + serviceWidth + "s | %-" + feeWidth + "s | %-" + statusWidth + "s%n";
        System.out.printf(format, "Tracking Number", "Parcel Type", "Receiver Name", "Chargeable Weight (kg)", "Delivery Service", "Delivery Fee", "Status");
        System.out.println("-".repeat(borderWidth));
    }

    // --- 3. Change Delivery Service ---
    public void changeDeliveryService(){
        printBanner("Change Delivery Service");
        if (!hasParcels()){
            return;
        }
        try {
            Parcel parcel = manager.findParcel(readLookupNumber());
            if(parcel.isDelivered()){
                System.out.println("Parcel already delivered!");
                return;
            }
            DeliveryStrategy newStrategy = readService();
            if(newStrategy.getServiceName().equals(parcel.getDeliveryStrategy().getServiceName())){
                System.out.println("Parcel is already using " + newStrategy.getServiceName() + " delivery.");
                return;
            }

            parcel.setDeliveryStrategy(newStrategy);
            System.out.printf("Delivery service changed! New fee: Php %,.2f%n", parcel.calculateDeliveryFee());
        } catch (ParcelNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParcelException e) {
            System.out.println(e.getMessage());
        }
    }

    private String readLookupNumber() {
        return input.readLine("Enter tracking number: ").trim().toUpperCase();
    }

    // --- 4. Mark as Delivered ---
    public void markAsDelivered(){
        printBanner("Mark as Delivered");
        if (!hasParcels() || !hasPendingParcels()){
            return;
        }
        try {
            Parcel parcel = manager.findParcel(readLookupNumber());
            if (parcel.isDelivered()){
                System.out.println("Parcel was already delivered.");
            } else {
                parcel.markAsDelivered();
                System.out.println("Parcel delivered successfully!");
            }
        } catch (ParcelNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean hasParcels() {
        if (manager.getAllParcels().isEmpty()){
            System.out.println("No parcels yet.");
            return false;
        }
        return true;
    }

    private static boolean hasPendingParcels() {
        for (Parcel p : manager.getAllParcels()){
            if(!p.isDelivered()){
                return true;
            }
        }
        System.out.println("All parcels are already delivered.");
        return false;
    }

    // --- Helpers ---
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
