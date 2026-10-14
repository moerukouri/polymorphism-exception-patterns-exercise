package exception;

public class DuplicateParcelException extends RuntimeException {
    public DuplicateParcelException(String message) {
        super("Parcel with tracking number " + message + " already exists.");
    }
    
}
