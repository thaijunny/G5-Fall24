/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public class MentorSkill {
    private Mentor mentor;
    private Skill skill;
    private  CV cv;
    private double rating;
    private List<Skill> skills;

    public MentorSkill(Mentor mentor, Skill skill, CV cv, double rating, List<Skill> skills) {
        this.mentor = mentor;
        this.skill = skill;
        this.cv = cv;
        this.rating = rating;
        this.skills = skills;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

   

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return "MentorSkill{" + "mentor=" + mentor + ", skill=" + skill + ", cv=" + cv + ", rating=" + rating + ", skills=" + skills + '}';
    }

   
    public MentorSkill() {
    }

   
    
}
