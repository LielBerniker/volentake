package com.example.model_voluntake_class;

public class Request_vol implements Request{
   private String vol_user_id;
   private String post_id;
   private String content;
   private int num_of_vol;
   private Status status;


    public Request_vol(String vol_user_id, String post_id, String content,int num_vol,Status stat) {
        this.vol_user_id = vol_user_id;
        this.post_id = post_id;
        this.content = content;
        this.num_of_vol = num_vol;
        this.status = stat;
    }
    public Request_vol() {

    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getNum_of_vol() {
        return num_of_vol;
    }

    public void setNum_of_vol(int num_of_vol) {
        this.num_of_vol = num_of_vol;
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
