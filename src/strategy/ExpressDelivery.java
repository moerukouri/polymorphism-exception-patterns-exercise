package strategy;

public class ExpressDelivery implements DeliveryStrategy {
    private final String SERVICE_NAME = "EXPRESS";
    private final double FEE_MULTIPLIER = 1.5;

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public double computeFee(double baseFee) {
        return baseFee * FEE_MULTIPLIER;
    }
    
}
