package com.vicenteaguilera.mylock.models;

public class User
{
    private String uid;

    public User(String uid)
    {
        this.uid=uid;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }
}
