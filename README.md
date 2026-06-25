# Rehlat Umrah Booking System
The Project is a desktop application i made for managing umrah trip bookings. the idea is that pilgrims can search and book travel packages, travel agents can add and manage the packages they offer, and the admin can see all users and manage accounts. everything is built in Java and the data is saved in a SQLite database so it doesnt reset when you close the app.

---

## What each user can do

Pilgrim:
- register a new account from the sign up screen
- login with email and password
- search for available umrah packages by name
- see the package details like hotel name, nights, rating, price and departure date
- book a package they like

Travel Agent:
- login using an account that the admin created for them
- see all packages in the system
- add new umrah packages with hotel info, flight details, price and dates
- any package they add will show up for pilgrims right away

Administrator:
- login with the admin account
- view all users in the system, there is 3 tabs (pilgrims, travel agents, admins)
- add new travel agent accounts
- view a full report of all registered users

---

## OOP Concepts i used

Encapsulation - all the model classes like User, Pilgrim, TravelAgent use private fields and public getters and setters, no field is accessed directly from outside the class

Inheritance - Pilgrim, TravelAgent and Administrator all extend the abstract User class, they share the common fields from User and each one adds its own stuff

Abstract Class - User is abstract so you cant create a User object directly, you have to use one of the subclasses

Interface Payable - the Booking class implements Payable which means it has processPayment() and getTotalCost() methods

Interface Reportable - Administrator implements Reportable so it has the generateReport() method

Method Overriding - each subclass overrides getRole(), showDashboard() and toString() from User with its own version

Exception Handling - i made 3 custom exceptions: BookingException, PaymentException and InvalidPackageException, they get thrown when something goes wrong in the booking or package logic

Polymorphism - in MainDashboard i use instanceof to check what type the user is and show different buttons depending on the role

---

## Project structure

```
src/
    Main.java
    interfaces/
        Payable.java
        Reportable.java
    exceptions/
        BookingException.java
        PaymentException.java
        InvalidPackageException.java
    model/
        User.java
        Pilgrim.java
        TravelAgent.java
        Administrator.java
        UmrahPackage.java
        Booking.java
    db/
        DatabaseConnection.java
        UserRepository.java
        PackageRepository.java
    gui/
        UITheme.java
        LoginForm.java
        SignUpForm.java
        MainDashboard.java
        PackageSearchForm.java
        PackageManagementForm.java
        ManageUsersForm.java
        ReportForm.java
```

---

## Database

the app uses SQLite and the database file is called rehlat_umrah.db, it gets created automatically the first time you run the program so you dont need to setup anything manually.

users table:
- user_id, full_name, email, password, phone, role
- passport_num and nationality columns are for pilgrims only
- agency_name and license_num are for travel agents only
- access_level is for admins only

packages table:
- package_id, package_name, hotel_makkah, hotel_madinah, flight_details
- num_nights, price, hotel_rating, depart_date, available

bookings table:
- booking_id, pilgrim_id (references users), package_id (references packages)
- status which can be PENDING CONFIRMED or CANCELLED
- paid which is 1 if paid and 0 if not

---
once the program runs for the first time the database and tables are created automatically and 3 default accounts and 3 sample packages are inserted.

---

## Test accounts

admin@rehlat.com / admin123
pilgrim@rehlat.com / pilgrim123
agent@rehlat.com / agent123

---


