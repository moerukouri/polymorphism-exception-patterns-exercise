package model;

import util.Validator;

public class Box extends Parcel {
    public static final double MAX_WEIGHT = 50.0;
    private static final double FEE_PER_KG = 40.0;
    private static final double VOLUMETRIC_DIVISOR = 3500.0;

    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight, int length, int width, int height) {
        super(trackingNumber, receiverName, weight);
        Validator.validateMax(weight, MAX_WEIGHT, "Weight");
        this.length = Validator.validatePositiveInt(length, "Length");
        this.width = Validator.validatePositiveInt(width, "Width");
        this.height = Validator.validatePositiveInt(height, "Height");
    }

    public double getVolumetricWeight() {
        return ((double) length * width * height) / VOLUMETRIC_DIVISOR;
    }

    @Override
    public ParcelType getParcelType() {
        return ParcelType.BOX;
    }

    @Override
    public double getChargeableWeight() {
        double bigger = Math.max(getWeight(), getVolumetricWeight());
        return Math.ceil(bigger);
    }

    @Override
    public double calculateBaseFee() {
        return FEE_PER_KG * getChargeableWeight();
    }
    
}
