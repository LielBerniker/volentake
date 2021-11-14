package com.example.myapplication;

public class Request_vol implements Request{
    Volunteer_user person;
    Association_post post;

    @Override
    public Association_post getPost() {
        return post;
    }

    @Override
    public void setPost(Association_post post) {
        this.post=post;
    }

    @Override
    public Volunteer_user getVol() {
        return person;
    }

    @Override
    public void setVolunteer(Volunteer_user vol) {
        this.person=vol;
    }
}
