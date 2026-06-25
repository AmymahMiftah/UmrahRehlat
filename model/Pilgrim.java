package rehlat.model;

import rehlat.exceptions.BookingException;
import rehlat.exceptions.InvalidPackageException;
import java.util.ArrayList;


public class Pilgrim extends User {

    private String passportNumber;
    private String nationality;
    private ArrayList<Booking> bookings;

    public Pilgrim(int userId, String fullName, String email, String password,
                   String phone, String passportNumber, String nationality) {
        super(userId, fullName, email, password, phone);
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.bookings = new ArrayList<Booking>();
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    @Override
    public String getRole() {
        return "Pilgrim";
    }

    @Override
    public void showDashboard() {
        System.out.println("=== Pilgrim Dashboard ===");
        System.out.println("Welcome, " + getFullName());
        System.out.println("Total Bookings: " + bookings.size());
    }

    public Booking bookPackage(UmrahPackage selectedPackage) throws BookingException, InvalidPackageException {
        if (selectedPackage == null) {
            throw new InvalidPackageException("Package does not exist");
        }
        if (selectedPackage.isAvailable() == false) {
            throw new InvalidPackageException("Package is not available");
        }
        if (passportNumber == null || passportNumber.equals("")) {
            throw new BookingException("Passport number is required before booking");
        }

        int newId = bookings.size() + 1;
        Booking newBooking = new Booking(newId, this, selectedPackage);
        bookings.add(newBooking);
        return newBooking;
    }

    @Override
    public String toString() {
        return super.toString() + " | Passport: " + passportNumber + " | Nationality: " + nationality;
    }
}
