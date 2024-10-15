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
import model.User;

/**
 *
 * @author ADMIN
 */

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/change-password"})
public class ChangePasswordController extends HttpServlet {

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
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
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
        handleChangePassword(request, response, user, session);

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
            response.sendRedirect("change-password");
            return;
        }

        // Check if new password matches confirmation
        if (!newPass.equals(confirmPass)) {
            session.setAttribute("notificationErr", "New password and confirmation password do not match.");
            response.sendRedirect("change-password");
            return;
        }

        // Update the password
        boolean success = userDAO.updatePassword(user.getEmail(), newPass);
        if (success) {
            session.setAttribute("notification", "Password changed successfully!");
            session.removeAttribute("account");
            response.sendRedirect("login");

        } else {
            session.setAttribute("notificationErr", "Failed to change password.");
            response.sendRedirect("change-password");

        }

    }

}
