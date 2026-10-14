package exception;

public class ParcelNotFoundException extends ParcelException {
    public ParcelNotFoundException(String message) {
        super("Parcel not found: " + message);
    }
    
}
