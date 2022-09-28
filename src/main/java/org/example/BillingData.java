package org.example;

public class BillingData {
    public String city;
    public String address;
    public String zip;
    public String phoneNumber;

    public BillingData(String city, String address, String zip, String phoneNumber){
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.zip = zip;
    }
}
