package model;

import util.Validator;

public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private Status status;

    protected Parcel(String trackingNumber, String receiverName, double weight) {
        this.trackingNumber = Validator.validateTrackingNumber(trackingNumber);
        this.receiverName = Validator.validateNonBlank(receiverName, "Receiver Name");
        this.weight = Validator.validatePositiveDouble(weight, "Weight");
        this.status = Status.PENDING;
    }

    public abstract ParcelType getParcelType();
    public abstract double getChargeableWeight();
    public abstract double calculateBaseFee();

    public void markAsDelivered() {
        this.status = Status.DELIVERED;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public String getReceiverName() { return receiverName; }
    public double getWeight() { return weight; }
    public boolean isDelivered() { return status == Status.DELIVERED; }
    public String getStatus() { return status.toString(); }
}