package com.progga.maternalcareadmin;

public class admin {
    private String uid,name,email,access;

    public admin() {
    }

    public admin(String uid, String name, String email, String access) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.access = access;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
