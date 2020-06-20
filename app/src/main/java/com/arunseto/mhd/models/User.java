package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 17-Jun-19.
 */

public class User {

    @SerializedName("ID_USER")
    private String id_user;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("PASSWORD")
    private String password;
    @SerializedName("NO_TELP")
    private String no_telp;
    @SerializedName("FIRST_NAME")
    private String first_name;
    @SerializedName("LAST_NAME")
    private String last_name;
    @SerializedName("LAST_LOGIN")
    private String last_login;
    @SerializedName("TYPE_LOGIN")
    private String type_login;
    @SerializedName("ROLE")
    private String role;
    @SerializedName("CREATED_AT")
    private String created_at;
    @SerializedName("PHOTO_URL")
    private String photo_url;

    public User(String id_user, String email, String password, String no_telp, String first_name, String last_name, String last_login, String type_login, String role, String created_at, String photo_url) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
        this.no_telp = no_telp;
        this.first_name = first_name;
        this.last_name = last_name;
        this.last_login = last_login;
        this.type_login = type_login;
        this.role = role;
        this.created_at = created_at;
        this.photo_url = photo_url;
    }

    public String getId_user() {
        return id_user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getLast_login() {
        return last_login;
    }

    public String getType_login() {
        return type_login;
    }

    public String getRole() {
        return role;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getPhoto_url() {
        return photo_url;
    }
}
