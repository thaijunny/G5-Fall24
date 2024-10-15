package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Mentor;
import model.MentorSchedule;
import model.SheduleStatus;
import model.Slot;

/**
 *
 * @author ADMIN
 */
public class MentorScheduleDAO extends DBContext {

    public List<MentorSchedule> getMentorSchedules(int mentorID) {
        List<MentorSchedule> mentorSchedules = new ArrayList<>();
        MentorDAO mentorDAO = new MentorDAO();
        SlotDAO slotDAO = new SlotDAO();

        // Using try-with-resources for auto-closing the resources
        String query = "SELECT * FROM Mentor_Schedule WHERE MentorID = ?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            
            // Set the mentorID parameter in the PreparedStatement
            preparedStatement.setInt(1, mentorID);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    MentorSchedule mentorSchedule = new MentorSchedule();
                    mentorSchedule.setId(rs.getInt("MentorScheduleID"));
                    // Fetch and set mentor details
                    Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                    mentorSchedule.setMentor(mentor);
                    
                    // Fetch and set slot details
                    Slot slot = slotDAO.getByID(rs.getInt("SlotID"));
                    mentorSchedule.setSlot(slot);
                    
                    mentorSchedule.setSheduleStatus(SheduleStatus.valueOf(rs.getString("status")));
                    // Add to the list
                    mentorSchedules.add(mentorSchedule);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mentorSchedules;
    }

    public static void main(String[] args) {
        MentorScheduleDAO dao = new MentorScheduleDAO();
        List<MentorSchedule> schedules = dao.getMentorSchedules(2);
        for (MentorSchedule schedule : schedules) {
            System.out.println(schedule);
        }
    }
}
