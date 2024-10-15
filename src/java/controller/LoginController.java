/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MenteeDAO;
import dal.MentorDAO;
import dal.UserDAO;
import dal.WalletDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.Wallet;
import model.enums.Role;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        // Save data to Cookies.
        String remember = request.getParameter("remember");
        WalletDAO walletDAO = new WalletDAO();
        //Check Email and Password in database:
        UserDAO ld = new UserDAO();
        User user = ld.getUserByAccoutAndPassword(email, password);
        if (user == null) {
            session.setAttribute("notificationErr", "The e-mail address and/or password you specified are not correct.");
            response.sendRedirect("login");
        } else if (user.getStatus().equals(Status.INACTIVE)) {
            session.setAttribute("notificationErr", "Please check your email address to active your account! ");
            response.sendRedirect("login");
        } else if (user.getStatus().equals(Status.BLOCKED)) {
            session.setAttribute("notificationErr", "Sorry! You had been blocked! ");
            response.sendRedirect("login");
        } else {

            // find out:
            //Generate 3 cookies to build remember me function:
            Cookie cuser = new Cookie("user", email);
            Cookie cpass = new Cookie("pass", password);
            Cookie cremember = new Cookie("remember", remember);
            if (remember != null) {

                // save account in cookie in 7 days.
                cuser.setMaxAge(60 * 60 * 24 * 7);
                cpass.setMaxAge(60 * 60 * 24 * 7);
                cremember.setMaxAge(60 * 60 * 24 * 7);
            } else {
                cuser.setMaxAge(0);
                cpass.setMaxAge(0);
                cremember.setMaxAge(0);
            }
            response.addCookie(cuser);
            response.addCookie(cpass);
            response.addCookie(cremember);
            Wallet wallet;
            if (user.getRole().equals(Role.MENTEE)) {
                MenteeDAO menteeDAO = new MenteeDAO();
                wallet = walletDAO.getWalletByUserId(menteeDAO.getByUserId(user.getId()).getId(), Role.MENTEE.name());
                if (wallet != null) {
                    session.setAttribute("myAmount", wallet.getAmount());
                } else {
                    session.setAttribute("myAmount", 0.0);
                }
            }
            if (user.getRole().equals(Role.MENTOR)) {
                MentorDAO mentorDAO = new MentorDAO();
                wallet = walletDAO.getWalletByUserId(mentorDAO.getByUserId(user.getId()).getId(), Role.MENTOR.name());
                if (wallet != null) {
                    session.setAttribute("myAmount", wallet.getAmount());
                } else {
                    session.setAttribute("myAmount", 0.0);
                }
            }
            session.setAttribute("account", user);
            switch (user.getRole()) {
                case MANAGER ->
                    response.sendRedirect("manager/dashboard");
                case ADMIN ->
                    response.sendRedirect("admin/dashboard");
                default ->
                    response.sendRedirect("home");
            }
        }

    }
}
