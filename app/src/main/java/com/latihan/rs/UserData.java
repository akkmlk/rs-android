package com.latihan.rs;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private Object username;

    @SerializedName("alamat")
    private Object alamat;

    public UserData(Integer id, String name, String username, String alamat) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.alamat = alamat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getAlamat() {
        return alamat;
    }

    public void setAlamat(Object alamat) {
        this.alamat = alamat;
    }
}
