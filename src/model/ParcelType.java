package model;

public enum ParcelType {
    DOCUMENT("Document", Document.MAX_WEIGHT),
    BOX("Box", Box.MAX_WEIGHT),
    FRAGILE("Fragile", Fragile.FRAGILE_MAX_WEIGHT);

    private final String label;
    private final double maxWeight;

    ParcelType(String label, double maxWeight){
        this.label = label;
        this.maxWeight = maxWeight;
    }

    public String getLabel() { return label; }
    public double getMaxWeight() { return maxWeight; }
}
