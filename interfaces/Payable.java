package rehlat.interfaces;

public interface Payable {

    boolean processPayment(double amount);

    double getTotalCost();
}
