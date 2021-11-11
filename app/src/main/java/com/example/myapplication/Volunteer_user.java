package com.example.myapplication;



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
    }
