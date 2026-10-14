package util;

import exception.InvalidInputException;

public class Validator {
    private Validator() {}

    public static String validateTrackingNumber(String trackingNumber) {
        if (trackingNumber == null || !trackingNumber.matches("^[ABC]\\d{4}$")) {
            throw new InvalidInputException("Tracking number must be in the format \"ABC\" followed by 4 digits.");
        }
        return trackingNumber;
    }

    public static String validateNonBlank(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return input;
    }

    public static double validateMaxWeight(double weight, double maxWeight, String fieldName) {
        if (weight > maxWeight) {
            throw new InvalidInputException(fieldName + " must not exceed " + maxWeight + ".");
        }
        return weight;
    }

    public static double validatePositiveDouble(double value, String fieldName) {
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
            throw new InvalidInputException(fieldName + " must be a positive number.");
        }
        return value;
    }

    public static int validatePositiveInt(int value, String fieldName) {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be a positive integer.");
        }
        return value;
    }

    public static int validateInRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new InvalidInputException(fieldName + " must be between " + min + " and " + max + ".");
        }
        return value;
    }
}
