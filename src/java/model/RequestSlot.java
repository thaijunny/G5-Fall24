/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class RequestSlot {
    private Request request;
    private MentorSchedule mentorSchedule;

    public RequestSlot() {
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public MentorSchedule getMentorSchedule() {
        return mentorSchedule;
    }

    public void setMentorSchedule(MentorSchedule mentorSchedule) {
        this.mentorSchedule = mentorSchedule;
    }
    
    
}
