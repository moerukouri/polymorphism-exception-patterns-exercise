package model;

public class Document extends Parcel {
    private final double BASE_FEE = 50.0;
    private final int MIN_PAGES = 1;
    private final int MAX_PAGES = 100;

    private final int pages;

    public Document(String trackingNumber, String receiverName, double weight, int pages) {
        super(trackingNumber, receiverName, weight);
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public ParcelType getParcelType() {
        return ParcelType.DOCUMENT;
    }

    @Override
    public double getChargeableWeight() {
        return getWeight();
    }

    @Override
    public double calculateBaseFee() {
        return BASE_FEE;
    }
}