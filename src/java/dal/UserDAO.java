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
import model.Blog;
import model.Mentee;
import model.Mentor;
import model.Request;
import model.Skill;
import model.User;
import model.UserDTO;
import model.enums.Gender;
import model.enums.RequestStatus;
import model.enums.Role;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
public class UserDAO extends DBContext {

    public boolean emailExists(String email) {
        String sql = "SELECT * FROM USER WHERE email = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();  // Return true if the email exists
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean phoneExists(String phone) {
        String sql = "SELECT * FROM USER WHERE phoneNumber = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();  // Return true if the email exists
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public int getUserIdByEmail(String email) {
        String sql = "SELECT UserID FROM USER WHERE email = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;  // Return -1 if not found
    }

    public User getUserByEmail(String email, String account) {
        String sql = "SELECT * FROM USER WHERE email = ? and account = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, account);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setEmail(rs.getString("email"));
                user.setAccount(rs.getString("account"));
                user.setFullname(rs.getString("fullname"));
                user.setDob(rs.getDate("DoB"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setAddress(rs.getString("Address"));
                user.setGender(Gender.valueOf(rs.getString("gender")));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setStatus(Status.valueOf(rs.getString("status")));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM USER WHERE UserID = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setEmail(rs.getString("email"));
                user.setAccount(rs.getString("account"));
                user.setFullname(rs.getString("fullname"));
                user.setDob(rs.getDate("DoB"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setAddress(rs.getString("Address"));
                user.setGender(Gender.valueOf(rs.getString("gender")));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setStatus(Status.valueOf(rs.getString("status")));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE USER SET password = ? WHERE email = ?";

        try ( Connection con = getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setString(2, email);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByAccoutAndPassword(String account, String password) {
        String sql = """
                     SELECT * from USER WHERE (account = ? or email = ?) and password = ?
                     """;
        Connection con;
        try {
            con = DBContext.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, account);
            ps.setString(2, account);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setEmail(rs.getString("email"));
                user.setAccount(rs.getString("account"));
                user.setFullname(rs.getString("fullname"));
                user.setDob(rs.getDate("DoB"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setAddress(rs.getString("Address"));
                user.setGender(Gender.valueOf(rs.getString("gender")));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setStatus(Status.valueOf(rs.getString("status")));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean insertUser(User user, String token) {
        String sql = """
                     INSERT INTO USER (fullname, email, password, phoneNumber, gender, address, account, role, status, activationToken)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                     """;
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getGender().toString());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getAccount());
            ps.setString(8, user.getRole().toString());
            ps.setString(9, user.getStatus().toString());
            ps.setString(10, token);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    // Method to activate a user by token
    public boolean activateUser(String token) {
        String sql = "UPDATE USER SET status = ?, activationToken = NULL WHERE activationToken = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, Status.ACTIVE.toString());
            ps.setString(2, token);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public String getUniqueAccountName(String baseAccount) {
        String account = baseAccount;
        int suffix = 0;

        while (checkAccountExists(account)) {
            suffix++;
            account = baseAccount + suffix;
        }

        return account;
    }

    // Method to check if the account name already exists
    private boolean checkAccountExists(String account) {
        String sql = "SELECT * FROM USER WHERE account = ?";
        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();
            return rs.next();  // Return true if the account exists
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateUser(User user) {
        String query = "UPDATE USER SET fullname = ?, DoB = ?, gender = ?, phoneNumber = ?, Address = ? WHERE UserID = ?";

        try ( Connection con = getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, user.getFullname());
            ps.setDate(2, user.getDob());
            ps.setString(3, user.getGender().toString());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getAddress());
            ps.setInt(6, user.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateUserStatus(User user) {
        String query = "UPDATE USER SET status = ? WHERE UserID = ?";

        try ( Connection con = getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, String.valueOf(user.getStatus()));
           
            ps.setInt(2, user.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserDTO> getAllUserWithParam(String searchParam, String status, String role) {
        List<UserDTO> users = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        MentorDAO mentorDAO = new MentorDAO();

        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("""
                SELECT 
                                                    u.*, 
                                                    m.MentorID, 
                                                    -- Calculate average star rating (distinct ratings)
                                                    (SELECT AVG(r.rate) 
                                                     FROM rating r 
                                                     WHERE r.MentorID = m.MentorID) AS totalStars, 
                                                
                                                    -- Count of currently accepted requests
                                                    COUNT(DISTINCT CASE WHEN req.Status = 'processing' THEN req.RequestID END) AS currentlyAcceptedRequests,
                                                
                                                    -- Calculate percentage of completed requests
                                                    (COUNT(DISTINCT CASE WHEN req.Status = 'close' THEN req.RequestID END) * 100.0) 
                                                    / NULLIF(COUNT(DISTINCT req.RequestID), 0) AS completedPercentage
                                         
                 FROM user u
                 LEFT JOIN mentor m ON u.userID = m.UserID
                 LEFT JOIN rating r ON r.MentorID = m.MentorID
                 LEFT JOIN request req ON req.MentorID = m.MentorID
                 WHERE 1 = 1 """);

            // Prepare the SQL statement with parameters
            PreparedStatement preparedStatement;
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND (u.fullName LIKE ? OR u.email LIKE ? OR u.account LIKE ? ) ");
                String searchPattern = "%" + searchParam.trim() + "%";
                list.add(searchPattern);  // For fullName
                list.add(searchPattern);  // For email
                list.add(searchPattern);  // For account
            }
            if (status != null && !status.trim().isEmpty()) {
                query.append(" AND u.Status = ? ");
                list.add(status);
            }
            if (role != null && !role.trim().isEmpty()) {
                query.append(" AND u.role = ? ");
                list.add(role);
            }

            query.append(" GROUP BY u.userID, m.MentorID ");
            query.append(" ORDER BY u.userID DESC ");
            preparedStatement = con.prepareStatement(query.toString());

            // Map the parameters
            mapParams(preparedStatement, list);

            // Execute the query and process the result set
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    UserDTO user = new UserDTO();
                    Mentor mentor = mentorDAO.getById(rs.getInt("MentorID"));
                    user.setMentor(mentor);
                    user.setId(rs.getInt("UserID"));
                    user.setEmail(rs.getString("email"));
                    user.setAccount(rs.getString("account"));
                    user.setFullname(rs.getString("fullname"));
                    user.setDob(rs.getDate("DoB"));
                    user.setTotalStar(rs.getDouble("totalStars"));
                    user.setRole(Role.valueOf(rs.getString("role")));
                    user.setStatus(Status.valueOf(rs.getString("Status").toUpperCase()));

                    // Set the number of currently accepted requests and completed percentage
                    user.setCurrentlyAcceptedRequests(rs.getInt("currentlyAcceptedRequests"));
                    user.setCompletedPercentage(rs.getDouble("completedPercentage"));

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
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

    public List<UserDTO> Paging(List<UserDTO> users, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, users.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return users.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        UserDAO o = new UserDAO();
        List<UserDTO> list = o.getAllUserWithParam(null, null, "MENTEE");
        for (UserDTO userDTO : list) {
            System.out.println(userDTO);
        }
    }
}
