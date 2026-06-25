package rehlat.model;

public class UmrahPackage {

    private int packageId;
    private String packageName;
    private String hotelMakkah;
    private String hotelMadinah;
    private String flightDetails;
    private int numberOfNights;
    private double price;
    private int hotelRating;
    private String departureDate;
    private boolean available;

    public UmrahPackage(int packageId, String packageName, String hotelMakkah, String hotelMadinah,
                        String flightDetails, int numberOfNights, double price,
                        int hotelRating, String departureDate, boolean available) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.hotelMakkah = hotelMakkah;
        this.hotelMadinah = hotelMadinah;
        this.flightDetails = flightDetails;
        this.numberOfNights = numberOfNights;
        this.price = price;
        this.hotelRating = hotelRating;
        this.departureDate = departureDate;
        this.available = available;
    }

    public int getPackageId() { return packageId; }

    public void setPackageId(int packageId) { this.packageId = packageId; }

    public String getPackageName() { return packageName; }

    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getHotelMakkah() { return hotelMakkah; }

    public void setHotelMakkah(String hotelMakkah) { this.hotelMakkah = hotelMakkah; }

    public String getHotelMadinah() { return hotelMadinah; }

    public void setHotelMadinah(String hotelMadinah) { this.hotelMadinah = hotelMadinah; }

    public String getFlightDetails() { return flightDetails; }

    public void setFlightDetails(String flightDetails) { this.flightDetails = flightDetails; }

    public int getNumberOfNights() { return numberOfNights; }

    public void setNumberOfNights(int numberOfNights) { this.numberOfNights = numberOfNights; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getHotelRating() { return hotelRating; }

    public void setHotelRating(int hotelRating) { this.hotelRating = hotelRating; }

    public String getDepartureDate() { return departureDate; }

    public void setDepartureDate(String departureDate) { this.departureDate = departureDate; }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Package[" + packageId + "] " + packageName
                + " | Nights: " + numberOfNights
                + " | Rating: " + hotelRating + " stars"
                + " | Price: $" + price
                + " | Available: " + available;
    }
}
