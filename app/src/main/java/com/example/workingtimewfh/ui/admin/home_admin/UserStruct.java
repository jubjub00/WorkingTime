package com.example.workingtimewfh.ui.admin.home_admin;

public class UserStruct {
    private String name;
    private String lastname;
    private String Id;
    private String tel;
    private String img_profile;

    public UserStruct() {
    }



    public UserStruct(String name1, String lastname, String Id, String tel, String img_profile) {
        this.name = name1;
        this.lastname = lastname;
        this.Id = Id;
        this.tel = tel;
        this.img_profile = img_profile;
    }

    public String getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(String img_profile) {
        this.img_profile = img_profile;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setID(String ID) {
        this.Id = ID;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLastname() {
        return lastname;
    }
    public String getId() {
        return Id;
    }
    public String getTel() {
        return tel;
    }

}