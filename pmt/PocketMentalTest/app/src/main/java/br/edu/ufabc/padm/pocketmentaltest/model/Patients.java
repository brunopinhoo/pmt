package br.edu.ufabc.padm.pocketmentaltest.model;

import java.io.Serializable;

/**
 * Created by leonardobenedeti on 02/05/15.
 */
public class Patients implements Serializable {

    private Long id;
    private String name;
    private String address;
    private String birth;
    private String schooling;
    private String gender;
    private String phone;
    private String susid;

    @Override
    public String toString() {
        return name.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {return birth; }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSchooling() { return schooling; }

    public void setSchooling(String schooling) { this.schooling = schooling; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getSusid() { return susid; }

    public void setSusid(String susid) { this.susid = susid; }
}
