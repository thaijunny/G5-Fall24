/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;


/**
 *
 * @author ADMIN
 */
public class Rating {
    private int id;
    private double rate;
    private String comment;
    private Mentor mentor;
    private Mentee mentee;
    private int menteeId;
    private Timestamp created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }

    public Rating() {
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Rating(int id, double rate, String comment, Mentor mentor, Mentee mentee, int menteeId, Timestamp created) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.mentor = mentor;
        this.mentee = mentee;
        this.menteeId = menteeId;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Rating{" + "id=" + id + ", rate=" + rate + ", comment=" + comment + ", mentor=" + mentor + ", mentee=" + mentee + ", menteeId=" + menteeId + ", created=" + created + '}';
    }

     

    
    
}
