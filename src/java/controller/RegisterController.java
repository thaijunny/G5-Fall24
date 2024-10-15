/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MenteeDAO;
import dal.MentorDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;
import model.User;
import model.enums.Gender;
import model.enums.Role;
import model.enums.Status;
import utility.EmailUtility;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();

        HttpSession session = request.getSession();
        // Retrieve form inputs
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String role = request.getParameter("role");
        String address = request.getParameter("address");

        // Check if email is already registered
        if (userDAO.emailExists(email)) {
            session.setAttribute("notificationErr", "Email already exists. Please use a different email.");
            request.setAttribute("fullname", fullname);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("confirmPassword", confirmPassword);
            request.setAttribute("phone", phone);
            request.setAttribute("gender", gender);
            request.setAttribute("role", role);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        if (userDAO.phoneExists(phone)) {
            session.setAttribute("notificationErr", "Phone already exists. Please use a different phone.");
            request.setAttribute("fullname", fullname);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("confirmPassword", confirmPassword);
            request.setAttribute("phone", phone);
            request.setAttribute("gender", gender);
            request.setAttribute("role", role);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Validate fields
        if (fullname == null || fullname.isEmpty()
                || email == null || !isValidEmail(email)
                || phone == null || !isValidPhone(phone)
                || gender == null || gender.isEmpty()
                || role == null || role.isEmpty()
                || address == null || address.isEmpty()
                || !password.equals(confirmPassword)) {

            session.setAttribute("notificationErr", "Please fill in all required fields and ensure valid data.");
            response.sendRedirect("register");
            return;
        }

        // Generate account name
        String account = generateAccountName(fullname);

        // Create User object
        account = userDAO.getUniqueAccountName(account);
        User newUser = new User();
        newUser.setFullname(fullname);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phone);
        newUser.setGender(Gender.valueOf(gender.toUpperCase()));
        newUser.setAddress(address);
        newUser.setAccount(account);
        newUser.setRole(Role.valueOf(role.toUpperCase()));
        newUser.setStatus(Status.INACTIVE);

        // Generate an activation token
        String token = generateActivationToken();

        // Insert user into DB
        boolean success = userDAO.insertUser(newUser, token);
        if (success) {
            int userId = userDAO.getUserIdByEmail(email);

            // Insert into Mentor or Mentee table based on role
            if (role.equalsIgnoreCase("MENTOR")) {
                MentorDAO mentorDAO = new MentorDAO();
                mentorDAO.insertMentor(userId);  // Insert into Mentor table
            } else if (role.equalsIgnoreCase("MENTEE")) {
                MenteeDAO menteeDAO = new MenteeDAO();
                menteeDAO.insertMentee(userId);  // Insert into Mentee table
            }
            // Send activation email asynchronously
            String activationLink = getBaseUrl(request) + "/activate?token=" + token;
            String subject = "Activate your account";
            String message = "Hello " + fullname + ",\n\nYour account has been created with account: " + account + ".\n"
                    + "Please click the following link to activate your account:\n" + activationLink;

            EmailUtility.sendEmail(email, subject, message);

            // Redirect to success page
            session.setAttribute("notification", "Registration successful! Please check your email to activate your account.");
            response.sendRedirect("login");
        } else {
            request.setAttribute("notificationErr", "Registration failed, please try again.");
            response.sendRedirect("register");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^0\\d{9}$";
        return phone.matches(phoneRegex);
    }

    private String generateAccountName(String fullname) {
        String[] nameParts = fullname.split(" ");
        String lastName = nameParts[nameParts.length - 1];
        String initials = "";
        for (int i = 0; i < nameParts.length - 1; i++) {
            initials += nameParts[i].charAt(0);
        }
        return lastName + initials.toUpperCase();
    }

    private String generateActivationToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[24];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();              // http or https
        String serverName = request.getServerName();      // localhost or domain name
        int serverPort = request.getServerPort();         // 8080, 443, etc.
        String contextPath = request.getContextPath();    // /your-app-name

        if (serverPort == 80 || serverPort == 443) {
            return scheme + "://" + serverName + contextPath;
        } else {
            return scheme + "://" + serverName + ":" + serverPort + contextPath;
        }
    }
}
