package model;

public class Fragile extends Box {
    private final double MAX_WEIGHT = 30.0;
    private final double HANDLING_FEE = 100.0;

    private final String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight, int length, int width, int height, String handlingNote) {
        super(trackingNumber, receiverName, weight, length, width, height);
        this.handlingNote = handlingNote;
    }

    public String getHandlingNote() {
        return handlingNote;
    }

    @Override
    public ParcelType getParcelType() {
        return ParcelType.FRAGILE;
    }

    @Override
    public double calculateBaseFee() {
        return super.calculateBaseFee() + HANDLING_FEE;
    }
    
}
