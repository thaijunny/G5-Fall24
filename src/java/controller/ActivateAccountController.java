package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "ActivateAccountController", urlPatterns = {"/activate"})
public class ActivateAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        boolean activated = userDAO.activateUser(token);

        if (activated) {
            session.setAttribute("notification", "Your account has been activated successfully.");
            response.sendRedirect("login");
        } else {
            request.setAttribute("notificationErr", "Invalid activation token.");
             response.sendRedirect("login");
        }
    }
}
