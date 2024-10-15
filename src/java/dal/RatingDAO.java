/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Mentee;
import model.Mentor;
import model.Rating;
import model.User;

/**
 *
 * @author ADMIN
 */
public class RatingDAO extends DBContext {

    public Rating getByMentorId(int id) {
        String sql = "Select * from Rating  where MentorID = ? order by created DESC";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            MentorDAO mdao = new MentorDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Rating rating = new Rating();
                Mentor m = mdao.getById(rs.getInt("MentorID"));
                rating.setMentor(m);
                rating.setComment(rs.getString("Comment"));
                rating.setRate(rs.getDouble("Rate"));
                rating.setMenteeId(rs.getInt("MenteeID"));
                rating.setId(rs.getInt("RatingID"));
                return rating;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        RatingDAO rdao = new RatingDAO();
        System.out.println(rdao.getByMentorId(2));
    }

    public List<Rating> getRatingsByMentorId(int mentorId) {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT * FROM rating WHERE mentorID = ? order by created DESC";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mentorId);
            MentorDAO mdao = new MentorDAO();
            MenteeDAO menteeDAO = new MenteeDAO();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Rating rating = new Rating();
                    rating.setId(rs.getInt("RatingID"));
                    rating.setRate(rs.getDouble("Rate"));
                    rating.setComment(rs.getString("Comment"));
                    rating.setMenteeId(rs.getInt("MentorID"));
                    Mentor m = mdao.getById(rs.getInt("MentorID"));
                    Mentee mentee = menteeDAO.getById(rs.getInt("MenteeID"));
                    rating.setMentee(mentee);
                    rating.setCreated(rs.getTimestamp("created"));
                    rating.setMentor(m);
                    ratings.add(rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratings;
    }

    public boolean insertRating(Rating rating) {
        String sql = "INSERT INTO Rating (Rate, Comment, MentorID, MenteeID) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, rating.getRate());
            ps.setString(2, rating.getComment());
            ps.setInt(3, rating.getMentor().getId()); // Mentor ID
            ps.setInt(4, rating.getMenteeId());       // Mentee ID

            return ps.executeUpdate() > 0; // Return true if insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there's an error
        }
    }

}
