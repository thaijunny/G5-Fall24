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
import model.enums.CVStatus;

public class CVDAO extends DBContext {

    public CV getByMentorId(int id) {
        String sql = "Select * from CV  where MentorID = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            MentorDAO mentorDAO = new MentorDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CV cv = new CV();
                Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                cv.setMentor(mentor);
                cv.setId(rs.getInt("CVID"));
                cv.setPrice(rs.getDouble("Price"));
                cv.setDescription(rs.getString("Description"));
                cv.setStatus(CVStatus.valueOf(rs.getString("Status")));
                return cv;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public CV getById(int id) {
        String sql = "Select * from CV  where CVID = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            MentorDAO mentorDAO = new MentorDAO();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CV cv = new CV();
                Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                cv.setMentor(mentor);
                cv.setId(rs.getInt("CVID"));
                cv.setPrice(rs.getDouble("Price"));
                cv.setDescription(rs.getString("Description"));
                cv.setStatus(CVStatus.valueOf(rs.getString("Status")));
                return cv;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<CV> getCVsWithParam(String searchParam, String status) {
        List<CV> cvs = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        MentorDAO mentorDAO = new MentorDAO();
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("""
                     select c.*, u.email, u.fullName, u.account 
                     from CV c 
                     JOIN mentor m ON m.MentorID = c.MentorID
                     JOIN user u ON u.userID = m.userID 
                     WHERE 1 = 1 """);

            // Prepare the SQL statement with parameters
            PreparedStatement preparedStatement;
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND (u.fullName LIKE ? OR u.email LIKE ? OR u.account LIKE ?) ");
                String searchPattern = "%" + searchParam.trim() + "%";
                list.add(searchPattern);  // For fullName
                list.add(searchPattern);  // For email
                list.add(searchPattern);  // For account
            }
            if (status != null && !status.trim().isEmpty()) {
                query.append(" AND c.Status = ? ");
                list.add(status);
            }

            query.append(" ORDER BY c.CVID DESC ");
            preparedStatement = con.prepareStatement(query.toString());

            // Map the parameters
            mapParams(preparedStatement, list);

            // Execute the query and process the result set
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    CV cv = new CV();
                    Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                    cv.setMentor(mentor);
                    cv.setId(rs.getInt("CVID"));
                    cv.setPrice(rs.getDouble("Price"));
                    cv.setDescription(rs.getString("Description"));
                    cv.setStatus(CVStatus.valueOf(rs.getString("Status")));
                    cvs.add(cv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cvs;
    }
  
    public boolean insertCV(CV cv) {
        String sql = "INSERT INTO CV (MentorID, Price, Description, Status) VALUES (?, ?, ?, ?)";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cv.getMentor().getId());
            ps.setDouble(2, cv.getPrice());
            ps.setString(3, cv.getDescription());
            ps.setString(4, cv.getStatus().name());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update an existing CV
    public boolean updateCV(CV cv) {
        String sql = "UPDATE CV SET Price = ?, Description = ?, Status = ? WHERE MentorID = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, cv.getPrice());
            ps.setString(2, cv.getDescription());
            ps.setString(3, cv.getStatus().name());
            ps.setInt(4, cv.getMentor().getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public List<CV> Paging(List<CV> cv, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, cv.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return cv.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        CVDAO aO = new CVDAO();
        CV m = aO.getByMentorId(2);
        System.out.println(m);
    }
}
