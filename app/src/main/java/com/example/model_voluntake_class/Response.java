package com.example.model_voluntake_class;

public class Response implements Response_Interface{

    private String vol_user_id;
    private String association_user_id;
    private String post_id;
    private Status status;

    public Response(String vol_user_id, String association_user_id, String post_id, Status status) {
        this.vol_user_id = vol_user_id;
        this.association_user_id = association_user_id;
        this.post_id = post_id;
        this.status = status;
    }
    public Response() {
    }

    @Override
    public String getVol_user_id() {
        return vol_user_id;
    }

    @Override
    public void setVol_user_id(String vol_user_id) {
        this.vol_user_id = vol_user_id;
    }

    public String getAssociation_user_id() {
        return association_user_id;
    }

    @Override
    public void setAssociation_user_id(String association_user_id) {
        this.association_user_id = association_user_id;
    }

    @Override
    public String getPost_id() {
        return post_id;
    }

    @Override
    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }


    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }


}
