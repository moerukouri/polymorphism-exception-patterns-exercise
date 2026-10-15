package manager;

import exception.DuplicateParcelException;
import exception.ParcelException;
import exception.ParcelNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Parcel;

public class ParcelManager {
    private static ParcelManager instance;
    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    private ParcelManager() {}

    public static ParcelManager getInstance() {
        if (instance == null) {
            instance = new ParcelManager();
        }
        return instance;
    }

    public boolean exists(String trackingNumber) {
        return parcels.containsKey(trackingNumber);
    }

    public void isNull(Parcel parcel){
        if (parcel == null){
            throw new ParcelException("Parcel cannot be null.");
        }
    }

    public void addParcel(Parcel parcel) {
        isNull(parcel);

        String trackingNumber = parcel.getTrackingNumber();
        if(exists(trackingNumber)){
            throw new DuplicateParcelException(trackingNumber);
        }
        parcels.put(trackingNumber, parcel);
    }

    public Parcel findParcel(String trackingNumber) {
        Parcel parcel = parcels.get(trackingNumber);
        if(parcel == null){
            throw new ParcelNotFoundException();
        }
        return parcel;
    }

    public Collection<Parcel> getAllParcels() {
        return Collections.unmodifiableCollection(parcels.values());
    }
}
