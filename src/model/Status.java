package model;

public enum Status {
    PENDING("Pending"),
    DELIVERED("Delivered");

    private final String label;

    Status(String label){
        this.label = label;
    }

    public String getLabel() { return label; }
}
