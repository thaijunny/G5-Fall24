package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import utility.EmailUtility;

import java.io.IOException;
import java.security.SecureRandom;

/**
 * Servlet implementation for handling password reset
 */

@WebServlet(name = "ForgetPasswordController", urlPatterns = {"/forget-password"})
public class ForgetPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show the forget password form
        request.getRequestDispatcher("forget-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String account = request.getParameter("account");
        HttpSession session = request.getSession();

        UserDAO userDao = new UserDAO();
        User user = userDao.getUserByEmail(email, account);

        // Check if the user exists with the provided email and account
        if (user != null) {
            // Generate a random password
            String newPassword = generateRandomPassword();

            // Save the new password in the session (or optionally in DB if needed)
            session.setAttribute("newPassword", newPassword);

            // Send email with the new password
            String subject = "Your New Password";
            String message = "Dear " + user.getFullname() + ",\n\nYour new password is: " + newPassword
                    + "\nPlease change your password after logging in.\n\nBest regards,\nHappy Programing Support Team";
            EmailUtility.sendEmail(email, subject, message);

            // Update the password in the database
            userDao.updatePassword(email, newPassword);

            // Notify the user that the new password has been sent
            session.setAttribute("notification", "A new password has been sent to your email.");
        } else {
            // If email and account don't match, show an error message
            session.setAttribute("notificationErr", "Invalid account or email. Please try again.");
        }

        // Redirect back to the forget password page
        response.sendRedirect("login");
    }

    // Method to generate a random password
    private String generateRandomPassword() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
}
