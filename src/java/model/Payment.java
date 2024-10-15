/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp ;
import model.enums.CVStatus;

/**
 *
 * @author ADMIN
 */
public class Payment {
    private  int id;
    private double amount;
    private Mentee mentee;
    private Mentor mentor;
    private CVStatus status;
    private Timestamp  createdDate;
    public Payment() {
    }

    public Payment(int id, double amount, Mentee mentee, Mentor mentor, CVStatus status, Timestamp createdDate) {
        this.id = id;
        this.amount = amount;
        this.mentee = mentee;
        this.mentor = mentor;
        this.status = status;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public CVStatus getStatus() {
        return status;
    }

    public void setStatus(CVStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", amount=" + amount + ", mentee=" + mentee + ", mentor=" + mentor + ", status=" + status + ", createdDate=" + createdDate + '}';
    }

    
    
    
    
}
