package com.example.myapplication;


import java.util.ArrayList;
import java.util.Date;

public interface Volunteer_user {
        /**
         * @return  return the the first name of user,
         */
        public String getFirst_name();
        /**
         * @param first_name - new first name
         * @return
         */
        public void setFirst_name(String first_name);
        /**
         * @return  return the the last name of user,
         */
        public String getLast_name();
        /**
         * @param last_name - new last name
         * @return
         */
        public void setLast_name(String last_name);
        /**
         * @return  return the address of user,
         */
        public Address getAddress();
        /**
         * @param address - new address name
         * @return
         */
        public void setAddress(Address address);
        /**
         * @return  return the phone number of user,
         */
        public String getPhone_num();
        /**
         * @param phone_num - new phone number
         * @return
         */
        public void setPhone_num(String phone_num);
    /**
     * @return  return the birth date of user,
     */
    public String  getBirth_date() ;
    /**
     * @param birth_date - new birth date
     * @return
     */
    public void setBirth_date(String birth_date);
    /**
     * @return  return email address of user,
     */
    public String getEmail();
    /**
     * @param email - new email address
     * @return
     */
    public void setEmail(String email);
    /**
     * @return  return user massages,
     */
    public ArrayList<String> getMassages_res();
    /**
     * @return  return user active posts,
     */
    public ArrayList<String> getActive_posts();

    }
