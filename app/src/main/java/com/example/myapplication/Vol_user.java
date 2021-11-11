package com.example.myapplication;

public class Vol_user implements Volunteer_user {
    String first_name;
    String last_name;
    Address address;
    String phone_num;

    public Vol_user(String first_name, String last_name, Address address, String phone_num) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_num = phone_num;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }


}
