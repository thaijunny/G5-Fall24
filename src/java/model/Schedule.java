/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import model.enums.CVStatus;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
public class Schedule {
    private int id;
    private Mentor mentor;
    private Mentee mentee;
    private MentorSchedule mentorSchedule;
    private Date scheduleDate;
    CVStatus status;

    public Schedule() {
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

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public MentorSchedule getMentorSchedule() {
        return mentorSchedule;
    }

    public void setMentorSchedule(MentorSchedule mentorSchedule) {
        this.mentorSchedule = mentorSchedule;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public CVStatus getStatus() {
        return status;
    }

    public void setStatus(CVStatus status) {
        this.status = status;
    }
    
    
}
