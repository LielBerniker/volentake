package com.example.myapplication;

public interface Request {


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
     * @return  return the post id ,
     */
    public String getPost_id() ;
    /**
     * @param post_id - new post
     * @return
     */
    public void setPost_id(String post_id);
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
     * @return  return num of vol ,
     */
    public int getNum_of_vol();
    /**
     * @param num_of_vol - new num of vol
     * @return
     */
    public void setNum_of_vol(int num_of_vol);
}
