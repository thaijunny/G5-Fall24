/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Mentee;
import model.Mentor;
import model.Request;
import model.Skill;
import model.Slot;
import model.enums.RequestStatus;
import model.enums.WeekDay;

/**
 *
 * @author ADMIN
 */
public class SlotDAO extends DBContext{
      public Slot getByID(int id) {
        String sql = "Select * from Slot  where SlotID = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            MentorDAO mentorDAO = new MentorDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Slot slot = new Slot();
                slot.setId(rs.getInt("SlotID"));
                slot.setTimeStart(rs.getTime("start_time"));
                slot.setTimeEnd(rs.getTime("end_time"));
                slot.setWeekDay(WeekDay.valueOf(rs.getString("WeekDay")));
                return slot;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
      public static void main(String[] args) {
        SlotDAO s = new SlotDAO();
          System.out.println(s.getByID(1));
    }
}
