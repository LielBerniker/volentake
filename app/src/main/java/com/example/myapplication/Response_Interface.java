package com.example.myapplication;

public interface Response_Interface {


    /**
     * @return  return the volunteer id,
     */
    public String getVol_user_id();
    /**
     * @param vol_user_id - new volunteer
     * @return
     */
    public void setVol_user_id(String vol_user_id);
    /**
     * @return  return the association id,
     */
    public String getAssociation_user_id();
    /**
     * @param association_user_id - new volunteer
     * @return
     */
    public void getAssociation_user_id(String association_user_id);

    /**
     * @return  return the content ,
     */
    public String getContent();
    /**
     * @param content - new content
     * @return
     */
    public void setContent(String content);
    /**
     * @return  return the status ,
     */
    public Status getStatus();
    /**
     * @param status - new status
     * @return
     */
    public void setStatus(Status status);
}
