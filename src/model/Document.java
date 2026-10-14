package model;

import util.Validator;

public class Document extends Parcel {
    private final double BASE_FEE = 50.0;
    private final double MAX_WEIGHT = 0.5;
    private final int MIN_PAGES = 1;
    private final int MAX_PAGES = 100;

    private final int pages;

    public Document(String trackingNumber, String receiverName, double weight, int pages) {
        super(trackingNumber, receiverName, weight);
        Validator.validateMaxWeight(weight, MAX_WEIGHT, "Weight");
        this.pages = Validator.validateInRange(pages, MIN_PAGES, MAX_PAGES, "Pages");
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