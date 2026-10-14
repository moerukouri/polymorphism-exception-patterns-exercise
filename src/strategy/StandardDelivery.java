package strategy;

public class StandardDelivery implements DeliveryStrategy {
    private final String SERVICE_NAME = "STANDARD";

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public double computeFee(double baseFee) {
        return baseFee;
    }
    
}
