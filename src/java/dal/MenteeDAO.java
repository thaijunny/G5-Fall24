package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Mentee;
import model.User;

public class MenteeDAO extends DBContext {

    public boolean insertMentee(int userId) {
        String sql = "INSERT INTO Mentee (UserID) VALUES (?)";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public Mentee getById(int id) {
        String sql = "Select * from Mentee  where MenteeID = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            UserDAO udao = new UserDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Mentee mentee = new Mentee();
                User u = udao.getUserById(rs.getInt("UserID"));
                mentee.setUser(u);
                mentee.setId(rs.getInt("MenteeID"));
                return mentee;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Mentee getByUserId(int id) {
        String sql = "Select * from Mentee  where userId = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            UserDAO udao = new UserDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Mentee mentee = new Mentee();
                User u = udao.getUserById(rs.getInt("UserID"));
                mentee.setUser(u);
                mentee.setId(rs.getInt("MenteeID"));
                return mentee;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        MenteeDAO o = new MenteeDAO();
        System.out.println(o.getByUserId(1));
    }
}
