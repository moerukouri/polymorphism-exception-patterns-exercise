package strategy;

public class SameDayDelivery implements DeliveryStrategy {
    private final String SERVICE_NAME = "SAME DAY";
    private final double FEE_MULTIPLIER = 2.0;
    public static final double MAX_CHARGEABLE_WEIGHT = 10.0;

    public static double getMaxChargeableWeight() { return MAX_CHARGEABLE_WEIGHT; }

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
