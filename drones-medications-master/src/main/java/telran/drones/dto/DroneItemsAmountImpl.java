package telran.drones.dto;
public class DroneItemsAmountImpl implements DroneItemsAmount {
    private String number;
    private long amount;

    public DroneItemsAmountImpl(String number, long amount) {
        this.number = number;
        this.amount = amount;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public long getAmount() {
        return amount;
    }
}