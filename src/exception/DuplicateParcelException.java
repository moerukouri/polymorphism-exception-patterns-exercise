package exception;

public class DuplicateParcelException extends ParcelException {
    public DuplicateParcelException(String message) {
        super("Parcel with tracking number " + message + " already exists.");
    }
    
}
