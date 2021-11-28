package com.example.myapplication;

public class Request_vol implements Request{
    String vol_user_id;
    String post_id;
    String content;

    public Request_vol(String vol_user_id, String post_id, String content) {
        this.vol_user_id = vol_user_id;
        this.post_id = post_id;
        this.content = content;
    }

    public String getVol_user_id() {
        return vol_user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVol_user_id(String vol_user_id) {
        this.vol_user_id = vol_user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
