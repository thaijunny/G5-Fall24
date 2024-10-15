/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.CVDAO;
import dal.PaymentDAO;
import dal.WalletDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.CV;
import model.Payment;
import model.User;
import model.Wallet;
import model.enums.CVStatus;
import model.enums.Role;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RechargeManagement", urlPatterns = {"/manager/manage-recharge"})

public class RechargeManagement extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("notificationErr", "You musr login first");
            response.sendRedirect("../login");

        } else if (!user.getRole().equals(Role.MANAGER)) {
            response.sendRedirect("../login");

        } else {
            String searchParam = request.getParameter("search");
            String statusParam = request.getParameter("status");
            String pageParam = request.getParameter("page");
            PaymentDAO paymentDAO = new PaymentDAO();
            int page = 1; // Default to the first page
            int pageSize = 6; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            List<Payment> payments = paymentDAO.getPaymentOfMenteeWithParam(searchParam, statusParam);
            List<Payment> pagingPayments = paymentDAO.Paging(payments, page, pageSize);

            request.setAttribute("payments", pagingPayments);
            request.setAttribute("search", searchParam);
            request.setAttribute("totalPages", payments.size() % pageSize == 0 ? (payments.size() / pageSize) : (payments.size() / pageSize + 1));
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("recharge-management.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            session.setAttribute("notificationErr", "You must log in first.");
            response.sendRedirect("../login");
            return;
        }

        if (!user.getRole().equals(Role.MANAGER)) {
            response.sendRedirect("../login");
            return;
        }

        String action = request.getParameter("action");
        int paymentId = Integer.parseInt(request.getParameter("id"));
        PaymentDAO paymentDAO = new PaymentDAO();
        WalletDAO walletDAO = new WalletDAO();

        Payment payment = paymentDAO.getById(paymentId);  // Retrieve the payment details

        if (payment == null) {
            session.setAttribute("notificationErr", "Payment not found.");
            response.sendRedirect("manage-recharge");
            return;
        }

        switch (action) {
            case "approve" -> {
                // Update payment status to APPROVED
                payment.setStatus(CVStatus.APPROVED);
                boolean isUpdated = paymentDAO.update(payment);

                if (isUpdated) {
                    System.out.println(payment);
                    // Check if mentee already has a wallet
                    Wallet wallet = walletDAO.getWalletByMenteeId(payment.getMentee().getId());
                    System.out.println(wallet);
                    if (wallet != null) {
                        // Update existing wallet balance
                        wallet.setAmount(wallet.getAmount() + payment.getAmount());
                        walletDAO.update(wallet);  // Update the wallet balance
                    } else {
                        // Create a new wallet entry
                        Wallet newWallet = new Wallet();
                        newWallet.setAmount(payment.getAmount());
                        newWallet.setMentee(payment.getMentee());
                        walletDAO.insert(newWallet);  // Insert a new wallet
                    }

                    session.setAttribute("notification", "Recharge approved successfully!");
                } else {
                    session.setAttribute("notificationErr", "Failed to approve recharge.");
                }

                response.sendRedirect("manage-recharge");
            }
            case "reject" -> {
                // Update payment status to REJECTED
                payment.setStatus(CVStatus.REJECTED);
                boolean isUpdated = paymentDAO.update(payment);

                if (isUpdated) {
                    session.setAttribute("notification", "Recharge rejected successfully!");
                } else {
                    session.setAttribute("notificationErr", "Failed to reject recharge.");
                }

                response.sendRedirect("manage-recharge");
            }
            default -> {
                session.setAttribute("notificationErr", "Invalid action.");
                response.sendRedirect("manage-recharge");
            }
        }
    }
}
