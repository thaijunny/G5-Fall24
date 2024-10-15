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
import java.util.List;
import model.User;
import model.UserDTO;
import model.enums.Gender;
import model.enums.Role;
import model.enums.Status;
import utility.EmailUtility;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UseManagement", urlPatterns = {"/admin/manage-user"})

public class UseManagement extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("notificationErr", "You musr login first");
            response.sendRedirect("../login");

        } else if (!user.getRole().equals(Role.ADMIN)) {
            response.sendRedirect("../login");

        } else {
            String searchParam = request.getParameter("search");
            String statusParam = request.getParameter("status");
            String roleParam = request.getParameter("role");
            String pageParam = request.getParameter("page");
            UserDAO userDAO = new UserDAO();
            int page = 1; // Default to the first page
            int pageSize = 6; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            String role = null;
            String status = null;
            if (statusParam != null && !statusParam.isEmpty()) {
                status = statusParam;
            }
            if (roleParam != null && !roleParam.isEmpty()) {
                role = roleParam;
            }
            List<UserDTO> users = userDAO.getAllUserWithParam(searchParam, status, role);
            List<UserDTO> pagingUser = userDAO.Paging(users, page, pageSize);

            for (UserDTO userDto : pagingUser) {
                double totalStars = userDto.getTotalStar(); // Assume you have a method getTotalStars()
                int wholeStars = (int) totalStars; // Calculate whole stars
                boolean hasHalfStar = (totalStars - wholeStars) >= 0.5; // Determine if there's a half star

                // Set the calculated values to userDTO
                userDto.setWholeStars(wholeStars);
                userDto.setHasHalfStar(hasHalfStar);
            }
            request.setAttribute("skills", pagingUser);
            request.setAttribute("title", "User Management");
            request.setAttribute("search", searchParam);
            request.setAttribute("totalPages", users.size() % pageSize == 0 ? (users.size() / pageSize) : (users.size() / pageSize + 1));
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("user-management.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        UserDAO userDAO = new UserDAO();

        if (user == null) {
            session.setAttribute("notificationErr", "You must login first");
            response.sendRedirect("../login");
        } else if (!user.getRole().equals(Role.ADMIN)) {
            response.sendRedirect("../login");
        } else {
            String action = request.getParameter("action");
            switch (action) {
                case "add" -> {
                    String fullName = request.getParameter("fullName");
                    String email = request.getParameter("email");
                    String password = generateRandomPassword();
                    String phone = request.getParameter("phoneNumber");
                    String gender = request.getParameter("gender");
                    String address = request.getParameter("address");
                    String role = request.getParameter("role");

                    // Server-side validation
                    if (fullName == null || fullName.isEmpty()
                            || email == null || !email.matches("[^@]+@[^.]+\\..+")
                            || password == null || password.isEmpty()
                            || phone == null || !phone.matches("\\d+")
                            || address == null || address.isEmpty()) {

                        session.setAttribute("notificationErr", "Invalid input. Please check the form fields.");
                        response.sendRedirect("manage-user");
                        return;
                    }

                    // Ensure email doesn't already exist
                    if (userDAO.emailExists(email)) {
                        session.setAttribute("notificationErr", "Email already exists.");
                        response.sendRedirect("manage-user");
                        return;
                    }
                    if (userDAO.phoneExists(phone)) {
                        session.setAttribute("notificationErr", "Phone already exists.");
                        response.sendRedirect("manage-user");
                        return;
                    }

                    // Create the user if validation passes
                    User newUser = new User();
                    newUser.setFullname(fullName);
                    newUser.setEmail(email);
                    newUser.setPassword(password); // Hash the password before saving
                    newUser.setPhoneNumber(phone);
                    newUser.setGender(Gender.valueOf(gender.toUpperCase()));
                    newUser.setAddress(address);
                    newUser.setRole(Role.valueOf(role)); 

                    newUser.setStatus(Status.ACTIVE);
                    String account = generateAccountName(fullName);

                    account = userDAO.getUniqueAccountName(account);
                    newUser.setAccount(account);
                    userDAO.insertUser(newUser, null);
                     int userId = userDAO.getUserIdByEmail(email);

                    if (role.equalsIgnoreCase("MENTOR")) {
                        MentorDAO mentorDAO = new MentorDAO();
                        mentorDAO.insertMentor(userId);
                    } else if (role.equalsIgnoreCase("MENTEE")) {
                        MenteeDAO menteeDAO = new MenteeDAO();
                        menteeDAO.insertMentee(userId);  // Insert into Mentee table
                    }
                    String subject = "Wellcome to Happy Programming";
                    String message = "Hello " + fullName + ",\n\nYour account has been created with account: " + account + " and password is:  "+password;

                    EmailUtility.sendEmail(email, subject, message);

                    session.setAttribute("notification", "New user added successfully!");
                    response.sendRedirect("manage-user");
                }
                case "block" -> {
                    int userIdToBlock = Integer.parseInt(request.getParameter("userId"));
                    User userToBlock = userDAO.getUserById(userIdToBlock);

                    if (userToBlock != null) {
                        // Ensure admin users cannot be blocked
                        if (userToBlock.getRole().equals(Role.ADMIN)) {
                            session.setAttribute("notificationErr", "You cannot block an admin user!");
                        } else {
                            userToBlock.setStatus(Status.BLOCKED);
                            userDAO.updateUserStatus(userToBlock);
                            session.setAttribute("notification", "User blocked successfully!");
                        }
                    } else {
                        session.setAttribute("notificationErr", "User not found!");
                    }
                    response.sendRedirect("manage-user");
                }

                case "unblock" -> {
                    int userIdToUnblock = Integer.parseInt(request.getParameter("userId"));
                    User userToUnblock = userDAO.getUserById(userIdToUnblock);

                    if (userToUnblock != null) {
                        userToUnblock.setStatus(Status.ACTIVE);
                        userDAO.updateUserStatus(userToUnblock);
                        session.setAttribute("notification", "User unblocked successfully!");
                    } else {
                        session.setAttribute("notificationErr", "User not found!");
                    }
                    response.sendRedirect("manage-user");
                }

                default -> {
                    session.setAttribute("notificationErr", "Invalid action.");
                    response.sendRedirect("manage-user");
                }

            }

        }

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
