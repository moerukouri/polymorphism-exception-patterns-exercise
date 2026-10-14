package model;

public class Box extends Parcel {
    private final double MAX_WEIGHT = 50.0;
    private final double FEE_PER_KG = 40.0;
    private final double VOLUMETRIC_DIVISOR = 3500.0;

    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight, int length, int width, int height) {
        super(trackingNumber, receiverName, weight);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double getVolumetricWeight() {
        return (length * width * height) / VOLUMETRIC_DIVISOR;
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
