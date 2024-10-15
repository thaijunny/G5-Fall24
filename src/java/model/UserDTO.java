/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import model.enums.Role;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
public class UserDTO {

    private int id;
    private String email;
    private String account;
    private Role role;
    private String fullname;
    private Date dob;
    private Status status;
    private double totalStar;
    private Mentor mentor;

    private int wholeStars;
    private boolean hasHalfStar;

    private int currentlyAcceptedRequests;
    private double completedPercentage;

    // Getters and setters for the new fields:
    public int getCurrentlyAcceptedRequests() {
        return currentlyAcceptedRequests;
    }

    public void setCurrentlyAcceptedRequests(int currentlyAcceptedRequests) {
        this.currentlyAcceptedRequests = currentlyAcceptedRequests;
    }

    public double getCompletedPercentage() {
        return completedPercentage;
    }

    public void setCompletedPercentage(double completedPercentage) {
        this.completedPercentage = completedPercentage;
    }

    public UserDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getTotalStar() {
        return totalStar;
    }

    public void setTotalStar(double totalStar) {
        this.totalStar = totalStar;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public int getWholeStars() {
        return wholeStars;
    }

    public void setWholeStars(int wholeStars) {
        this.wholeStars = wholeStars;
    }

    public boolean isHasHalfStar() {
        return hasHalfStar;
    }

    public void setHasHalfStar(boolean hasHalfStar) {
        this.hasHalfStar = hasHalfStar;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "id=" + id + ", email=" + email + ", account=" + account + ", role=" + role + ", fullname=" + fullname + ", dob=" + dob + ", status=" + status + ", totalStar=" + totalStar + ", mentor=" + mentor + ", wholeStars=" + wholeStars + ", hasHalfStar=" + hasHalfStar + '}';
    }

}
