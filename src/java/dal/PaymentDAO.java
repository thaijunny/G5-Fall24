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
import model.Mentee;
import model.Mentor;
import model.Payment;
import model.enums.CVStatus;

/**
 *
 * @author ADMIN
 */
public class PaymentDAO extends DBContext {

    public boolean insert(Payment payment) {
        String sql = "INSERT INTO payment (menteeId, amount, status, createdDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getMentee().getId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getStatus().name());
            stmt.setTimestamp(4, payment.getCreatedDate());

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment getById(int id) {
        String sql = "SELECT * FROM Payment WHERE PaymentID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);  // Set the Payment ID parameter

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Payment payment = new Payment();
                    MenteeDAO menteeDAO = new MenteeDAO();
                    Mentee mentee = menteeDAO.getById(rs.getInt("MenteeID"));

                    payment.setId(rs.getInt("PaymentID"));
                    payment.setAmount(rs.getDouble("Amount"));
                    payment.setMentee(mentee);
                    payment.setCreatedDate(rs.getTimestamp("createdDate"));
                    payment.setStatus(CVStatus.valueOf(rs.getString("Status").toUpperCase()));
                    return payment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Payment payment) {
        String sql = "UPDATE Payment SET amount = ?, status = ?, createdDate = ?, MenteeID = ? WHERE PaymentID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, payment.getAmount());
            stmt.setString(2, payment.getStatus().name());  // Save status as a string
            stmt.setTimestamp(3, payment.getCreatedDate());  // Save the timestamp
            stmt.setInt(4, payment.getMentee().getId());  // Set the MenteeID
            stmt.setInt(5, payment.getId());  // Set the PaymentID for the WHERE clause

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> getPaymentOfMenteeWithParam(String searchParam, String status) {
        List<Payment> cvs = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        MenteeDAO menteeDAO = new MenteeDAO();
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("""
                     select c.*, u.email, u.fullName, u.account 
                     from Payment c 
                     JOIN mentee m ON m.MenteeID = c.MenteeID
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

            query.append(" ORDER BY c.createdDate DESC ");
            preparedStatement = con.prepareStatement(query.toString());

            // Map the parameters
            mapParams(preparedStatement, list);

            // Execute the query and process the result set
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment();
                    Mentee mentee = menteeDAO.getById(rs.getInt("MenteeID"));
                    payment.setMentee(mentee);
                    payment.setId(rs.getInt("PaymentID"));
                    payment.setAmount(rs.getDouble("Amount"));
                    payment.setCreatedDate(rs.getTimestamp("createdDate"));
                    payment.setStatus(CVStatus.valueOf(rs.getString("Status").toUpperCase()));
                    cvs.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cvs;
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

    public List<Payment> Paging(List<Payment> payments, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, payments.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return payments.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        PaymentDAO aO = new PaymentDAO();
      Payment l = aO.getById(1);
        System.out.println(l);   
    }
}
