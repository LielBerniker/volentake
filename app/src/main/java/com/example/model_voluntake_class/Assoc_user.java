package com.example.model_voluntake_class;

import java.util.ArrayList;

public class Assoc_user implements Association_user {
   private String phone_num;
    private Address address;
    private String name;
    private String Email;
    private String about;
   public ArrayList<String> posts;
    public ArrayList<String> massages_req;
    boolean HasMassage;

    public Assoc_user(String phone_num, Address address, String name,String email,String about) {
        this.phone_num = phone_num;
        this.address = address;
        this.name = name;
        this.Email = email;
        this.about = about;
       this.posts = new ArrayList<>();
       posts.add("0");
        this.massages_req = new ArrayList<>();
        massages_req.add("0");
        this.HasMassage = false;
    }
    public Assoc_user() {

    }

    public boolean isHasMassage() {
        return HasMassage;
    }

    public void setHasMassage(boolean hasMassage) {
        HasMassage = hasMassage;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getMassages_req() {
        return massages_req;
    }

    public String getAbout() {
        return about;
    }

    public ArrayList<String> getPosts() {
        return posts;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
