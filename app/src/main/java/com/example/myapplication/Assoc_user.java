package com.example.myapplication;

import java.util.HashMap;

public class Assoc_user implements Association_user {
    String phone_num;
    Address address;
    String name;
    String Email;
    HashMap<String, Association_post> posts ;

    public Assoc_user(String phone_num, Address address, String name,String email) {
        this.phone_num = phone_num;
        this.address = address;
        this.name = name;
        this.Email = email;
       this.posts = new HashMap<String, Association_post>();
    }
    public void add_post(Association_post post)
    {
        posts.put(post.getId(),post);
    }

    public void add_post(String name, Address location, int num_of_participants, String type, String phone_number)
    {
      Association_post cur_post = new Assoc_post(name,location,num_of_participants,type,phone_number);
      posts.put(name,cur_post);
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
