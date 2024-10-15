/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import model.enums.Gender;
import model.enums.Role;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
public class User {

    private int id;
    private String email;
    private String account;
    private String password;
    private Role role;
    private String fullname;
    private Date dob;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private Status status;
    private String activeToken;

    public User() {
    }

    public User(int id, String email, String account, String password, Role role, String fullname, Date dob, String phoneNumber, String address, Gender gender, Status status) {
        this.id = id;
        this.email = email;
        this.account = account;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.status = status;
    }

    public User(int id, String email, String account, String password, Role role, String fullname, Date dob, String phoneNumber, String address, Gender gender, Status status, String activeToken) {
        this.id = id;
        this.email = email;
        this.account = account;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.status = status;
        this.activeToken = activeToken;
    }

    public String getActiveToken() {
        return activeToken;
    }

    public void setActiveToken(String activeToken) {
        this.activeToken = activeToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", account=" + account + ", password=" + password + ", role=" + role + ", fullname=" + fullname + ", dob=" + dob + ", phoneNumber=" + phoneNumber + ", address=" + address + ", gender=" + gender + ", status=" + status + '}';
    }

}
