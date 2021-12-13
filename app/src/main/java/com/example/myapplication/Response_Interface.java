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
     * @param association_user_id - new volunteer
     * @return
     */
    public void setAssociation_user_id(String association_user_id);
    /**
     * @return  return assoc uder id
     */
    public String getAssociation_user_id();
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

    /**
     * @return  return post id
     */
    public String getPost_id() ;
    /**
     * @param post_id - new post id
     * @return
     */
    public void setPost_id(String post_id);


}
