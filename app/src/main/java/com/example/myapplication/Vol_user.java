package com.example.myapplication;

import java.util.ArrayList;
import java.util.Date;

public class Vol_user implements Volunteer_user {
   private String first_name;
    private String last_name;
  private Address address;
   private String phone_num;
   private String birth_date;
   private String Email;
    public ArrayList<String> massages_res;
    public ArrayList<String> active_posts;

    public Vol_user(String first_name, String last_name, Address address, String phone_num,String birthdate, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_num = phone_num;
        this.birth_date = birthdate;
        this.Email = email;
        this.massages_res = new ArrayList<>();
        massages_res.add("0");
        this.active_posts = new ArrayList<>();
        active_posts.add("0");
    }
    public Vol_user() {

    }

    public ArrayList<String> getActive_posts() {
        return active_posts;
    }

    public ArrayList<String> getMassages_res() {
        return massages_res;
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

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
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
