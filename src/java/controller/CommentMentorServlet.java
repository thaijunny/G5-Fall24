package controller;

import dal.MentorDAO;
import dal.RatingDAO;
import dal.MenteeDAO;
import model.Mentor;
import model.Mentee;
import model.Rating;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import model.User;
import model.enums.Role;

@WebServlet("/comment-mentor")
public class CommentMentorServlet extends HttpServlet {

    // Handle GET request to show mentor info and comments
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null && user.getRole().equals(Role.MENTEE)) {
            int mentorId = Integer.parseInt(request.getParameter("id"));
            MentorDAO mentorDAO = new MentorDAO();
            RatingDAO ratingDAO = new RatingDAO();

            // Get mentor information
            Mentor mentor = mentorDAO.getById(mentorId);

            // Get existing comments and ratings
            List<Rating> ratings = ratingDAO.getRatingsByMentorId(mentorId);

            // Set attributes to be used in the JSP page
            request.setAttribute("mentor", mentor);
            request.setAttribute("ratings", ratings);

            // Forward the request to the JSP page to display mentor info and comments
            request.getRequestDispatcher("comment-mentor.jsp").forward(request, response);
        } else {
            session.setAttribute("notificationErr", "You must login first!");
            response.sendRedirect("login");
        }
    }

    // Handle POST request to add new comment and rating
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null && user.getRole().equals(Role.MENTEE)) {

            int mentorId = Integer.parseInt(request.getParameter("mentorId"));
            int rate = Integer.parseInt(request.getParameter("rate"));
            String comment = request.getParameter("comment");
            MenteeDAO menteeDAO = new MenteeDAO();
            Mentee mentee = menteeDAO.getByUserId(user.getId());
            // Create a new Rating object
            Rating rating = new Rating();
            rating.setRate(rate);
            rating.setComment(comment);
            rating.setMenteeId(mentee.getId());  // Assuming mentee is logged in and available in session
            rating.setMentor(new MentorDAO().getById(mentorId));
            rating.setCreated(new Timestamp(System.currentTimeMillis()));

            // Save the rating using DAO
            RatingDAO ratingDAO = new RatingDAO();
            boolean success = ratingDAO.insertRating(rating);

            // Redirect back to the mentor's comment page after submission
            if (success) {
                session.setAttribute("notification", "Comment successfully");
                response.sendRedirect("comment-mentor?id=" + mentorId);
            } else {
                response.sendRedirect("comment-mentor?id=" + mentorId);
            }
        } else {
            session.setAttribute("notificationErr", "You must login first!");
            response.sendRedirect("login");
        }
    }
}
