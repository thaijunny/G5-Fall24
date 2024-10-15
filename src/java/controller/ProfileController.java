/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import model.User;
import model.enums.Gender;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("notificationErr", "You musr login first");
            response.sendRedirect("login");

        } else {
            User currentUser = userDAO.getUserById(user.getId());
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            session.setAttribute("notificationErr", "You must login first");
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");

        if (action.equals("update")) {
            // Handle Profile Update
            handleProfileUpdate(request, response, user, session);

        } else if (action.equals("change-pass")) {
            // Handle Password Change
            handleChangePassword(request, response, user, session);
        }
    }

    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response, User user, HttpSession session)
            throws IOException {
        // Get updated information from the form
        String fullName = request.getParameter("fullName");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Update user object with the new information
        user.setFullname(fullName);
        user.setDob(Date.valueOf(dob)); // Convert String to Date
        user.setGender(Gender.valueOf(gender.toUpperCase())); // Convert String to Enum
        user.setPhoneNumber(phone);
        user.setAddress(address);

        // Update the user in the database
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updateUser(user);

        if (success) {
            session.setAttribute("notification", "Profile updated successfully!");
        } else {
            session.setAttribute("notificationErr", "Failed to update profile.");
        }

        response.sendRedirect("profile");
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response, User user, HttpSession session)
            throws IOException {
        // Get password details from the form
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String confirmPass = request.getParameter("confirmPass");

        UserDAO userDAO = new UserDAO();

        // Validate old password
        User userFromDB = userDAO.getUserByAccoutAndPassword(user.getAccount(), oldPass);
        if (userFromDB == null) {
            session.setAttribute("notificationErr", "Old password is incorrect.");
            response.sendRedirect("profile");
            return;
        }

        // Check if new password matches confirmation
        if (!newPass.equals(confirmPass)) {
            session.setAttribute("notificationErr", "New password and confirmation password do not match.");
            response.sendRedirect("profile");
            return;
        }

        // Update the password
        boolean success = userDAO.updatePassword(user.getEmail(), newPass);
        if (success) {
            session.setAttribute("notification", "Password changed successfully!");
        } else {
            session.setAttribute("notificationErr", "Failed to change password.");
        }

        // Redirect back to the profile page
        response.sendRedirect("profile");
    }

}
