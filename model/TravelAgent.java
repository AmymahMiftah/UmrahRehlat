package rehlat.model;

import rehlat.exceptions.InvalidPackageException;
import java.util.ArrayList;

public class TravelAgent extends User {

    private String agencyName;
    private String licenseNumber;
    private ArrayList<UmrahPackage> managedPackages;


    public TravelAgent(int userId, String fullName, String email, String password,
                       String phone, String agencyName, String licenseNumber) {
        super(userId, fullName, email, password, phone);
        this.agencyName = agencyName;
        this.licenseNumber = licenseNumber;
        this.managedPackages = new ArrayList<UmrahPackage>();
    }

    public String getAgencyName() {
        return agencyName;
    }


    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public ArrayList<UmrahPackage> getManagedPackages() {
        return managedPackages;
    }

    @Override
    public String getRole() {
        return "TravelAgent";
    }

    @Override
    public void showDashboard() {
        System.out.println("=== Travel Agent Dashboard ===");
        System.out.println("Agency: " + agencyName);
        System.out.println("Packages: " + managedPackages.size());
    }

    public void createPackage(UmrahPackage newPackage) throws InvalidPackageException {
        if (newPackage == null) {
            throw new InvalidPackageException("Cannot add an empty package.");
        }
        managedPackages.add(newPackage);
        System.out.println("Package added: " + newPackage.getPackageName());
    }

    @Override
    public String toString() {
        return super.toString() + " | Agency: " + agencyName;
    }
}
