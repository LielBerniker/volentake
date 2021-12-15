package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

public interface Association_user {

    /**
     * @return  return the Association phone number ,
     */
    public String getPhone_num();
    /**
     * @param phone_num - new phine number
     * @return
     */
    public void setPhone_num(String phone_num);
    /**
     * @return  return the Association address,
     */
    public Address getAddress();
    /**
     * @param address - new address
     * @return
     */
    public void setAddress(Address address);

    /**
     * @return  return the Association name,
     */
    public String getName();
    /**
     * @param name - new association name
     * @return
     */
    public void setName(String name);
    /**
     * @return  return the Association email address,
     */
    public String getEmail();
    /**
     * @param email - new association email
     * @return
     */
    public void setEmail(String email);
    /**
     * @return  return the Association information description ,
     */
    public String getAbout();
    /**
     * @param about - new association  Association information description
     * @return
     */
    public void setAbout(String about);
    /**
     * @return  return the Association posts ,
     */
    public ArrayList<String> getPosts();
    /**
     * @return  return the Association massages ,
     */
    public ArrayList<String> getMassages_req();
}
