/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.CV;
import model.Mentor;
import model.MentorSkill;
import model.Rating;
import model.Skill;

/**
 *
 * @author ADMIN
 */
public class MentorSkillDAO extends DBContext {

    public List<MentorSkill> getCVOfSkillsWithParam(int id, String searchParam) {
        List<MentorSkill> mentorSkills = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        MentorDAO mentorDAO = new MentorDAO();
        CVDAO cvdao = new CVDAO();
        SkillDAO skillDAO = new SkillDAO();
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("""
            SELECT ms.*, m.*, u.*, s.*, c.*, AVG(r.rate) as avgRating
            FROM mentor_skill ms
            LEFT JOIN Mentor m ON m.MentorID = ms.MentorID
            LEFT JOIN User u ON u.userID = m.userID
            LEFT JOIN Skill s ON s.skillID = ms.skillID
            LEFT JOIN CV c ON m.MentorID = c.MentorID
            LEFT JOIN rating r ON r.mentorID = m.MentorID
            WHERE s.skillID = ?
        """);
            list.add(id);  // Add skillID to the list of parameters

            // Add optional search parameters if provided
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND (u.fullName LIKE ? OR u.email LIKE ? OR u.account LIKE ? ) ");
                String searchPattern = "%" + searchParam.trim() + "%";
                list.add(searchPattern);  // Add search parameter for fullName, email, and account
                list.add(searchPattern);
                list.add(searchPattern);
            }

            query.append(" AND c.status = 'APPROVED' ");
            query.append(" GROUP BY ms.MentorID, c.CVID ");  // Group by mentor and CV to calculate average rating
            query.append(" ORDER BY c.CVID DESC ");  // Order by CVID

            // Prepare the SQL statement
            PreparedStatement preparedStatement = con.prepareStatement(query.toString());

            // Map the parameters to the prepared statement
            mapParams(preparedStatement, list);

            // Execute the query and process the result set
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    // Create a new MentorSkill object for each row
                    MentorSkill mentorSkill = new MentorSkill();

                    // Get the Mentor object from the result set
                    Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                    mentorSkill.setMentor(mentor);

                    // Get the CV object
                    CV cv = cvdao.getById(rs.getInt("CVID"));
                    mentorSkill.setCv(cv);

                    // Get the Skill object
                    Skill skill = skillDAO.getSkillById(rs.getInt("skillID"));
                    mentorSkill.setSkill(skill);

                    // Get all associated skills for the mentor
                    List<Skill> listSkill = skillDAO.getMentorSkill(rs.getInt("MentorID"));
                    mentorSkill.setSkills(listSkill);

                    // Set the average rating
                    double avgRating = rs.getDouble("avgRating");
                    mentorSkill.setRating(avgRating); 

                    // Add the MentorSkill object to the list
                    mentorSkills.add(mentorSkill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception for debugging
        }

        // Return the list of MentorSkill objects
        return mentorSkills;
    }

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }

        }
    }

    public List<MentorSkill> Paging(List<MentorSkill> mentorSkills, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, mentorSkills.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return mentorSkills.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        MentorSkillDAO m = new MentorSkillDAO();
        //List<MentorSkill> list = m.Paging(m.getCVOfSkillsWithParam(1));
//        for (MentorSkill mentorSkill : list) {
//            System.out.println(mentorSkill);
//        }
    }
}
