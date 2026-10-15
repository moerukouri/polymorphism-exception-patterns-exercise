package model;

import exception.ServiceNotAllowedException;
import strategy.*;
import util.Validator;

public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private Status status;
    private DeliveryStrategy deliveryStrategy = new StandardDelivery();

    protected Parcel(String trackingNumber, String receiverName, double weight) {
        this.trackingNumber = Validator.validateTrackingNumber(trackingNumber);
        this.receiverName = Validator.validateNonBlank(receiverName, "Receiver Name");
        this.weight = Validator.validatePositiveDouble(weight, "Weight");
        this.status = Status.PENDING;
    }

    public abstract ParcelType getParcelType();
    public abstract double getChargeableWeight();
    public abstract double calculateBaseFee();

    public double calculateDeliveryFee() {
        double baseFee = calculateBaseFee();
        return deliveryStrategy.computeFee(baseFee);
    }

    public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy) {
        if (!deliveryStrategy.isAllowedFor(getChargeableWeight())) {
            throw new ServiceNotAllowedException("Same Day delivery is only for parcels up to " + (int) SameDayDelivery.getMaxChargeableWeight() + "kg!");
        }
        this.deliveryStrategy = deliveryStrategy;
    }

    public void markAsDelivered() {
        if(status == Status.DELIVERED){
            throw new IllegalStateException("Parcel was already delivered.");
        }
        this.status = Status.DELIVERED;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public String getReceiverName() { return receiverName; }
    public double getWeight() { return weight; }
    public boolean isDelivered() { return status == Status.DELIVERED; }
    public String getStatus() { return status.getLabel(); }
    public DeliveryStrategy getDeliveryStrategy() { return deliveryStrategy; }

    public String toTableRow(int trkNumWidth, int typeWidth, int nameWidth, int chargeWeightWidth, int serviceWidth, int feeWidth, int statusWidth){
        String format = " %-" + trkNumWidth + "s | %-" + typeWidth + "s | %-" + nameWidth + "s | %,-" + chargeWeightWidth + ".2f | %-" + serviceWidth + "s | Php %,-" + (feeWidth - 4) + ".2f | %-" + statusWidth + "s";
        return String.format(format, trackingNumber, getParcelType().getLabel(), receiverName, getChargeableWeight(), deliveryStrategy.getServiceName(), calculateDeliveryFee(), getStatus());
    }
}