package manager;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Collections;
import model.Parcel;
import exception.DuplicateParcelException;
import exception.ParcelNotFoundException;

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

    public void addParcel(Parcel parcel) {
        String trackingNumber = parcel.getTrackingNumber();
        if(exists(trackingNumber)){
            throw new DuplicateParcelException(trackingNumber);
        }
        parcels.put(trackingNumber, parcel);
    }

    public Parcel findParcel(String trackingNumber) {
        Parcel parcel = parcels.get(trackingNumber);
        if(parcel == null){
            throw new ParcelNotFoundException(trackingNumber);
        }
        return parcel;
    }

    public Collection<Parcel> getAllParcels() {
        return Collections.unmodifiableCollection(parcels.values());
    }
}
