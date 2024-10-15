/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.enums.CVStatus;

/**
 *
 * @author ADMIN
 */
public class CV {

    private int id;
    private Mentor mentor;
    private String description;
    private double price;
    private CVStatus status;

    public CV() {
    }

    public CV(int id, Mentor mentor, String description, double price, CVStatus status) {
        this.id = id;
        this.mentor = mentor;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CVStatus getStatus() {
        return status;
    }

    public void setStatus(CVStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CV{" + "id=" + id + ", mentor=" + mentor + ", description=" + description + ", price=" + price + ", status=" + status + '}';
    }
   
    
}
