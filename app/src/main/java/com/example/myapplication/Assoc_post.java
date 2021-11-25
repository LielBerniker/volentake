package com.example.myapplication;

public class Assoc_post implements Association_post {
   private String user_id;
   private String name;
   private Address location;
   private int num_of_participants;
   private String type;
   private String phone_number;
   private String description;

    public Assoc_post(String name, Address location, int num_of_participants, String type, String phone_number,String user_id) {
        this.name = name;
        this.location = location;
        this.num_of_participants = num_of_participants;
        this.type = type;
        this.phone_number = phone_number;
        this.user_id = user_id;
    }

    @Override
    public String getId() { return user_id; }
    public void setId(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public int getNum_of_participants() {
        return num_of_participants;
    }

    public void setNum_of_participants(int num_of_participants) {
        this.num_of_participants = num_of_participants;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
