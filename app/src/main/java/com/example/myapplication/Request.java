package com.example.myapplication;

public interface Request {
    /**
     * @return  return the post ,
     */
    public Association_post getPost();
    /**
     * @param post - new post
     * @return
     */
    public void setPost(Association_post post);
    /**
     * @return  return the volunteer ,
     */
    public Volunteer_user getVol();
    /**
     * @param vol - new volunteer
     * @return
     */
    public void setVolunteer(Volunteer_user vol);
}
