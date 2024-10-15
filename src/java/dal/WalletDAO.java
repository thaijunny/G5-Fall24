/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Wallet;
import java.sql.*;
import model.Mentee;

/**
 *
 * @author ADMIN
 */
public class WalletDAO extends DBContext {

    // Method to get a wallet by menteeId
    public Wallet getWalletByMenteeId(int menteeId) {
        String sql = "SELECT * FROM wallet WHERE menteeId = ?";
        MenteeDAO menteeDAO = new MenteeDAO();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, menteeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt("WalletID"));
                wallet.setAmount(rs.getDouble("amount"));
                Mentee m = menteeDAO.getById(menteeId);
                wallet.setMentee(m);
                return wallet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to insert a new wallet
    public boolean insert(Wallet wallet) {
        String sql = "INSERT INTO wallet (menteeId, amount) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wallet.getMentee().getId());
            stmt.setDouble(2, wallet.getAmount());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update the wallet
    public boolean update(Wallet wallet) {
        String sql = "UPDATE wallet SET amount = ? WHERE menteeId = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, wallet.getAmount());
            stmt.setInt(2, wallet.getMentee().getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

    // Method to get a wallet by mentorId
    public Wallet getWalletByMentorId(int mentorId) {
        String sql = "SELECT * FROM wallet WHERE mentorId = ?";
        MentorDAO mentorDAO = new MentorDAO();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mentorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt("WalletID"));
                wallet.setAmount(rs.getDouble("amount"));
                wallet.setMentor(mentorDAO.getById(mentorId)); // Assuming MentorDAO has a getById method
                return wallet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Unified method to get wallet by user ID and role
    public Wallet getWalletByUserId(int userId, String role) {
        if (role.equals("MENTEE")) {
            return getWalletByMenteeId(userId);
        } else if (role.equals("MENTOR")) {
            return getWalletByMentorId(userId);
        }
        return null;
    }

    public static void main(String[] args) {
        WalletDAO pdao = new WalletDAO();
        System.out.println(pdao.getWalletByMenteeId(1));
    }

}
