package strategy;

public class SameDayDelivery implements DeliveryStrategy {
    private final String SERVICE_NAME = "SAME DAY";
    private final double FEE_MULTIPLIER = 2.0;
    private final double MAX_CHARGEABLE_WEIGHT = 10.0;

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public double computeFee(double baseFee) {
        return baseFee * FEE_MULTIPLIER;
    }

    @Override
    public boolean isAllowedFor(double chargeableWeight) {
        return chargeableWeight <= MAX_CHARGEABLE_WEIGHT;
    }
    
}
