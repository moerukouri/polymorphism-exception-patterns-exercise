package strategy;

public interface DeliveryStrategy {
    String getServiceName();
    double computeFee(double baseFee);
    default boolean isAllowedFor(double chargeableWeight) {
        return true;
    }
}
