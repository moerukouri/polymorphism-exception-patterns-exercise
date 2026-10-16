package util;

import exception.InvalidInputException;
import java.util.Scanner;

public class InputReader {
    private static final Scanner sc = new Scanner(System.in);

    public String readLine(String prompt){
        System.out.print(prompt);
        return sc.nextLine();
    }

    public String readText(String prompt, String fieldName){
        String value = null;
        boolean valid = false;
        do { 
            try {
                value = Validator.validateNonBlank(readLine(prompt), fieldName);
                valid = true;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        return value;
    }

    public String readTrackingNumber(String prompt){
        String value = null;
        boolean valid = false;
        do { 
            try {
                value = Validator.validateTrackingNumber(readLine(prompt).trim());
                valid = true;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        return value;
    }

    public int readInteger(String prompt, int min, int max, String field){
        int value = 0;
        boolean valid = false;
        do { 
            try {
                String input = readLine(prompt).trim();

                if(!input.matches("^(0|[1-9][0-9]*)$")){
                    throw new InvalidInputException(field + " must be a valid integer with no leading zeros.");
                }

                int parsed = Integer.parseInt(input);
                value = Validator.validateInRange(parsed, min, max, field);
                valid = true;        
            } catch (NumberFormatException e) {
                System.out.println(field + " is too large.");
            } catch (InvalidInputException e){
                System.out.println(e.getMessage());
            }
        } while (!valid);
        return value;
    }

    public double readDouble(String prompt, double max, String field){
        double value = 0;
        boolean valid = false;
        do { 
            try {
                String input = readLine(prompt).trim();
                
                if(!input.matches("^(0|[1-9][0-9]*)(\\.[0-9]{1,3})?$")){
                    throw new InvalidInputException(field + " must be a valid number with up to 3 decimal places and no leading zeros.");
                }

                double parsed = Double.parseDouble(input);
                Validator.validatePositiveDouble(parsed, field);
                value = Validator.validateMax(parsed, max, field);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println(field + " is too large.");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        return value;
    }
}
