package id.ac.stiki.doleno.mhd.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 17-Jun-19.
 */

public class User {

    @SerializedName("ID_USER")
    private int id_user;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("PASSWORD")
    private String password;
    @SerializedName("FIRST_NAME")
    private String first_name;
    @SerializedName("LAST_NAME")
    private String last_name;
    @SerializedName("NO_TELP")
    private String no_telp;
    @SerializedName("SEX")
    private int sex;
    @SerializedName("BIRTH_DATE")
    private String birth_date;
    @SerializedName("CITY")
    private String city;
    @SerializedName("PHOTO_URL")
    private String photo_url;
    @SerializedName("ROLE")
    private int role;
    @SerializedName("LAST_LOGIN")
    private String last_login;
    @SerializedName("TYPE_LOGIN")
    private int type_login;
    @SerializedName("CREATED_AT")
    private String created_at;

    public User(int id_user, String email, String password, String first_name, String last_name, String no_telp, int sex, String birth_date, String city, String photo_url, int role, String last_login, int type_login, String created_at) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.no_telp = no_telp;
        this.sex = sex;
        this.birth_date = birth_date;
        this.city = city;
        this.photo_url = photo_url;
        this.role = role;
        this.last_login = last_login;
        this.type_login = type_login;
        this.created_at = created_at;
    }

    public int getId_user() {
        return id_user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public int getSex() {
        return sex;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getCity() {
        return city;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public int getRole() {
        return role;
    }

    public String getLast_login() {
        return last_login;
    }

    public int getType_login() {
        return type_login;
    }

    public String getCreated_at() {
        return created_at;
    }
}
