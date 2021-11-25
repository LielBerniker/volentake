package com.example.myapplication;

import java.util.Date;

public class Vol_user implements Volunteer_user {
   private String first_name;
    private String last_name;
  private Address address;
   private String phone_num;
   private Date birth_date;
   private String Email;

    public Vol_user(String first_name, String last_name, Address address, String phone_num,Date birthdate, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_num = phone_num;
        this.birth_date = birthdate;
        this.Email = email;
    }
    public Vol_user() {

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

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
