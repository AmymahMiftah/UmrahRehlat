package rehlat.model;

import rehlat.interfaces.Payable;


public class Booking implements Payable {

    private int bookingId;
    private Pilgrim pilgrim;
    private UmrahPackage umrahPackage;
    private String status;
    private boolean paid;

    public Booking(int bookingId, Pilgrim pilgrim, UmrahPackage umrahPackage) {
        this.bookingId = bookingId;
        this.pilgrim = pilgrim;
        this.umrahPackage = umrahPackage;
        this.status = "PENDING";
        this.paid = false;
    }


    public int getBookingId() { return bookingId; }

    public Pilgrim getPilgrim() { return pilgrim; }

    public UmrahPackage getUmrahPackage() { return umrahPackage; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public boolean isPaid() { return paid; }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Payment failed: amount must be greater than zero");
            return false;
        }
        if (amount < umrahPackage.getPrice()) {
            System.out.println("Payment failed: amount is less than the package price");
            return false;
        }
        this.paid = true;
        this.status = "CONFIRMED";
        System.out.println("Payment of $" + amount + " confirmed for Booking #" + bookingId);
        return true;
    }


    @Override
    public double getTotalCost() {
        return umrahPackage.getPrice();
    }

    public void cancelBooking() {
        this.status = "CANCELLED";
    }

    @Override
    public String toString() {
        return "Booking #" + bookingId
                + " | Pilgrim: " + pilgrim.getFullName()
                + " | Package: " + umrahPackage.getPackageName()
                + " | Status: " + status
                + " | Paid: " + paid;
    }
}
