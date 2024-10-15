package controller;

import dal.RequestDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Request;
import model.User;
import model.enums.Role;

@WebServlet(name = "RequestStatisticController", urlPatterns = {"/request-statistic"})
public class RequestStatisticController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user != null && user.getRole().equals(Role.MENTEE)) {
            String searchParam = request.getParameter("search");
            String statusParam = request.getParameter("status");
            String pageParam = request.getParameter("page");

            int page = 1; // Default page
            int pageSize = 6; // Number of records per page
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }

            RequestDAO requestDAO = new RequestDAO();
            List<Request> requests = requestDAO.getRequestsForMenteeWithParam(searchParam, statusParam);
            List<Request> pagingRequests = requestDAO.Paging(requests, page, pageSize);

            // Calculate total number of requests and mentors
            int totalRequests = requests.size();
            long totalMentors = requests.stream().map(r -> r.getMentor().getId()).distinct().count();

            request.setAttribute("requests", pagingRequests);
            request.setAttribute("title", "Statistic of requests");
            request.setAttribute("totalRequests", totalRequests);
            request.setAttribute("totalMentors", totalMentors);
            request.setAttribute("totalPages", (int) Math.ceil((double) totalRequests / pageSize));
            request.setAttribute("currentPage", page);
            request.setAttribute("search", searchParam);
            request.setAttribute("status", statusParam);

            request.getRequestDispatcher("statistic-requests.jsp").forward(request, response);
        } else {
            session.setAttribute("notificationErr", "You must login first!");
            response.sendRedirect("login");
        }
    }

    
}
