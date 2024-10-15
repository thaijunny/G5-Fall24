/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author ADMIN
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Mentee;
import model.Mentor;
import model.Request;
import model.Skill;
import model.enums.RequestStatus;

public class RequestDAO {

    public boolean saveRequest(Request request) {
        String sql = "INSERT INTO request (Title, start_date,end_date, content, skillId, mentorId, status, createDate, menteeID, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";

        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, request.getSubject());
            ps.setDate(2, request.getStartDate());
            ps.setDate(3, request.getEndDate());
            ps.setString(4, request.getContent());
            ps.setInt(5, request.getSkill().getId());
            ps.setInt(6, request.getMentor().getId());
            ps.setString(7, request.getStatus().name());
            ps.setDate(8, request.getCreateDate());
            ps.setInt(9, request.getMentee().getId());
            ps.setString(10, request.getPrice());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    public List<Request> getRequestsForMenteeWithParam(String searchParam, String status) {
        List<Request> cvs = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        MentorDAO mentorDAO = new MentorDAO();
        MenteeDAO menteeDAO = new MenteeDAO();
        SkillDAO skillDAO = new SkillDAO();
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("""
                     select r.*, u.email, u.fullName, u.account 
                     from request r 
                     JOIN mentor m ON m.MentorID = r.MentorID
                     JOIN user u ON u.userID = m.userID 
                     WHERE 1 = 1 """);

            // Prepare the SQL statement with parameters
            PreparedStatement preparedStatement;
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND (u.fullName LIKE ? OR u.email LIKE ? OR u.account LIKE ? OR r.Title like ?) ");
                String searchPattern = "%" + searchParam.trim() + "%";
                list.add(searchPattern);  // For fullName
                list.add(searchPattern);  // For email
                list.add(searchPattern);  // For account
                list.add(searchPattern);  // For title
            }
            if (status != null && !status.trim().isEmpty()) {
                query.append(" AND r.Status = ? ");
                list.add(status);
            }

            query.append(" ORDER BY r.createDate DESC ");
            preparedStatement = con.prepareStatement(query.toString());

            // Map the parameters
            mapParams(preparedStatement, list);

            // Execute the query and process the result set
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request();
                    Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                    Mentee mentee = menteeDAO.getById(rs.getInt("MenteeID"));
                    request.setMentor(mentor);
                    request.setMentee(mentee);
                    Skill skill = skillDAO.getSkillById(rs.getInt("skillId"));
                    request.setSkill(skill);
                    request.setRequestId(rs.getInt("RequestID"));
                    request.setContent(rs.getString("content"));
                    request.setSubject(rs.getString("Title"));
                    request.setStartDate(rs.getDate("start_date"));
                    request.setEndDate(rs.getDate("end_date"));
                    request.setCreateDate(rs.getDate("createDate"));
                    request.setPrice(rs.getString("totalPrice"));
                    request.setStatus(RequestStatus.valueOf(rs.getString("Status").toUpperCase()));
                    cvs.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cvs;
    }

    public Request getByID(int id) {
        String sql = "Select * from Request  where RequestID = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            MentorDAO mentorDAO = new MentorDAO();
            MenteeDAO menteeDAO = new MenteeDAO();
            SkillDAO skillDAO = new SkillDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Request request = new Request();
                Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                Mentee mentee = menteeDAO.getById(rs.getInt("MenteeID"));
                request.setMentor(mentor);
                request.setMentee(mentee);
                Skill skill = skillDAO.getSkillById(rs.getInt("skillId"));
                request.setSkill(skill);
                request.setRequestId(rs.getInt("RequestID"));
                request.setContent(rs.getString("content"));
                request.setSubject(rs.getString("Title"));
                request.setPrice(rs.getString("totalPrice"));
                request.setStartDate(rs.getDate("start_date"));
                request.setEndDate(rs.getDate("end_date"));
                request.setCreateDate(rs.getDate("createDate"));
                request.setStatus(RequestStatus.valueOf(rs.getString("Status").toUpperCase()));
                return request;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
     public Request getLastesRequest() {
        String sql = "Select * from Request order by requestID desc limit 1 ";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            MentorDAO mentorDAO = new MentorDAO();
            MenteeDAO menteeDAO = new MenteeDAO();
            SkillDAO skillDAO = new SkillDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Request request = new Request();
                Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                Mentee mentee = menteeDAO.getById(rs.getInt("MenteeID"));
                request.setMentor(mentor);
                request.setMentee(mentee);
                Skill skill = skillDAO.getSkillById(rs.getInt("skillId"));
                request.setSkill(skill);
                request.setRequestId(rs.getInt("RequestID"));
                request.setContent(rs.getString("content"));
                request.setSubject(rs.getString("Title"));
                request.setPrice(rs.getString("totalPrice"));
                request.setStartDate(rs.getDate("start_date"));
                request.setEndDate(rs.getDate("end_date"));
                request.setCreateDate(rs.getDate("createDate"));
                request.setStatus(RequestStatus.valueOf(rs.getString("Status").toUpperCase()));
                return request;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Request request) {
        String sql = "UPDATE request SET Title = ?, end_date = ?,start_date = ?,  content = ?, skillId = ?, status = ? WHERE RequestID = ?";

        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, request.getSubject()); // Update the title
            ps.setDate(2, request.getEndDate()); // Update the deadline (date + time)
            ps.setDate(3, request.getStartDate()); // Update the deadline (date + time)
            ps.setString(4, request.getContent()); // Update the content of the request
            ps.setInt(5, request.getSkill().getId()); // Update the skill associated with the request
            ps.setString(6, request.getStatus().name()); // Update the status of the request
            ps.setInt(7, request.getRequestId()); // Use RequestID to identify the record to update

            return ps.executeUpdate() > 0; // Returns true if the update was successful, otherwise false
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there's an error
        }
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

    public List<Request> Paging(List<Request> requests, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, requests.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return requests.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        RequestDAO r = new RequestDAO();
        System.out.println(r.getLastesRequest());
    }

    public boolean insertRequestSchedule(int requestId, int slotId) {
        String sql = "INSERT INTO request_slots (RequestID, MentorScheduleID) VALUES (?, ?)";

        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ps.setInt(2, slotId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
