package com.example.myapplication;

public class Response implements Response_Interface{

    private String vol_user_id;
    private String association_user_id;
    private Status status;
    private String content;

    @Override
    public String getVol_user_id() {
        return vol_user_id;
    }

    @Override
    public void setVol_user_id(String vol_user_id) {
        this.vol_user_id = vol_user_id;
    }

    @Override
    public String getAssociation_user_id() {
        return association_user_id;
    }

    @Override
    public void getAssociation_user_id(String association_user_id) {
        this.association_user_id = association_user_id;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
