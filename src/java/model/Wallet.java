/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author ADMIN
 */
public class Wallet {
    private  int id;
    private double amount;
    private Mentee mentee;
    private Mentor mentor;
    public Wallet() {
    }

    public Wallet(int id, double amount, Mentee mentee, Mentor mentor) {
        this.id = id;
        this.amount = amount;
        this.mentee = mentee;
        this.mentor = mentor;
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

   

    @Override
    public String toString() {
        return "Wallet{" + "id=" + id + ", amount=" + amount + ", mentee=" + mentee + ", mentor=" + mentor + '}';
    }

    
    
    
}
