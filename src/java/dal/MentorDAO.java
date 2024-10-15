package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Mentor;
import model.User;

public class MentorDAO extends DBContext {

    public boolean insertMentor(int userId) {
        String sql = "INSERT INTO Mentor (UserID) VALUES (?)";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateMentor(Mentor mentor) {
        String sql = "UPDATE mentor SET avatar = ? WHERE MentorID = ?";

        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Set parameters for the update
            ps.setString(1, mentor.getAvatar()); // Update the avatar
            ps.setInt(2, mentor.getId()); // Update by MentorID

            // Execute the update
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there is an exception
        }
    }

    public Mentor getByUserId(int id) {
        String sql = "Select * from mentor  where userId = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            UserDAO udao = new UserDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Mentor mentor = new Mentor();
                User u = udao.getUserById(rs.getInt("UserID"));
                mentor.setUser(u);
                mentor.setId(rs.getInt("MentorID"));
                mentor.setAvatar(rs.getString("avatar"));
                return mentor;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Mentor> getAllMentors() {
        String sql = "Select * from mentor ";
        List<Mentor> list = new ArrayList<>();
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            UserDAO udao = new UserDAO();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Mentor mentor = new Mentor();
                User u = udao.getUserById(rs.getInt("UserID"));
                mentor.setUser(u);
                mentor.setId(rs.getInt("MentorID"));
                mentor.setAvatar(rs.getString("avatar"));
                list.add(mentor);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Mentor getById(int id) {
        String sql = "Select * from mentor  where MentorID = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            UserDAO udao = new UserDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Mentor mentor = new Mentor();
                User u = udao.getUserById(rs.getInt("UserID"));
                mentor.setUser(u);
                mentor.setAvatar(rs.getString("avatar"));

                mentor.setId(rs.getInt("MentorID"));
                return mentor;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        MentorDAO aO = new MentorDAO();
        Mentor m = aO.getByUserId(1);
        System.out.println(m);
    }
}
