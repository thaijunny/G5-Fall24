/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class MentorSchedule {
    private int id;
    Mentor mentor;
    Slot slot;
    SheduleStatus sheduleStatus;
    public MentorSchedule() {
    }

    public MentorSchedule(Mentor mentor, Slot slot) {
        this.mentor = mentor;
        this.slot = slot;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public SheduleStatus getSheduleStatus() {
        return sheduleStatus;
    }

    public void setSheduleStatus(SheduleStatus sheduleStatus) {
        this.sheduleStatus = sheduleStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MentorSchedule{" + "id=" + id + ", mentor=" + mentor + ", slot=" + slot + ", sheduleStatus=" + sheduleStatus + '}';
    }

   
    
    
}
