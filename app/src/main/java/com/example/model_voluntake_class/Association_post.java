package com.example.model_voluntake_class;

public interface Association_post {

    /**
     * @return  return the post name ,
     */
    public String getName();
    /**
     * @param name - new post number
     * @return
     */
    public void setName(String name);
    /**
     * @return  return the post location ,
     */
    public Address getLocation() ;
    /**
     * @param location - new loaction
     * @return
     */
    public void setLocation(Address location);
    /**
     * @return  return the post number of participants ,
     */
    public int getNum_of_participants();
    /**
     * @param num_of_participants - new number of participants
     * @return
     */
    public void setNum_of_participants(int num_of_participants);
    /**
     * @return  return the post type,
     */
    public String getType();
    /**
     * @param type - new post type
     * @return
     */
    public void setType(String type);
    /**
     * @return  return the post phone number ,
     */
    public String getPhone_number();
    /**
     * @param phone_number - new phone number
     * @return
     */
    public void setPhone_number(String phone_number);
    /**
     * @return  return the post description ,
     */
    public String getDescription();
    /**
     * @param description - new post description
     * @return
     */
    public void setDescription(String description);

}
