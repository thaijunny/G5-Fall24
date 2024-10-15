/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MenteeDAO;
import dal.PaymentDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Mentee;
import model.Payment;
import model.User;
import model.enums.CVStatus;
import model.enums.Role;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RechargeController", urlPatterns = {"/recharge"})
public class RechargeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null && user.getRole().equals(Role.MENTEE)) {
            String amount = request.getParameter("amount");
            request.setAttribute("amount", amount);
            request.setAttribute("title", "Recharge");
            request.getRequestDispatcher("recharge.jsp").forward(request, response);
        } else {
            session.setAttribute("notificationErr", "You must login first!");
            response.sendRedirect("login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user != null && user.getRole().equals(Role.MENTEE)) {
            MenteeDAO menteeDAO = new MenteeDAO();
            PaymentDAO paymentDAO = new PaymentDAO();

            Mentee mentee = menteeDAO.getByUserId(user.getId());
            int amount;

            try {
                amount = Integer.parseInt(request.getParameter("amount"));
            } catch (NumberFormatException e) {
                session.setAttribute("notificationErr", "Invalid amount entered. Please try again.");
                response.sendRedirect("recharge");
                return;
            }

            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setMentee(mentee);
            payment.setStatus(CVStatus.PENDING);
            payment.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
            boolean isAdd = paymentDAO.insert(payment);

            if (isAdd) {
                session.setAttribute("notification", "Recharge request successful. Waiting for manager approval.");
            } else {
                session.setAttribute("notificationErr", "Recharge request failed. Please try again.");
            }
            response.sendRedirect("recharge");
        } else {
            session.setAttribute("notificationErr", "You must log in first!");
            response.sendRedirect("login");
        }
    }
}
