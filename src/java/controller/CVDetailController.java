package controller;

import dal.MentorDAO;
import dal.CVDAO;
import dal.RatingDAO;  // Add this to fetch ratings
import dal.SkillDAO;
import model.Mentor;
import model.CV;
import model.Skill;
import model.Rating;  // Add this to use ratings
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CVDetailController", urlPatterns = {"/cv-detail"})

public class CVDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CVDAO cvDAO = new CVDAO();
        SkillDAO skillDAO = new SkillDAO();
        RatingDAO ratingDAO = new RatingDAO();  // Add this to fetch ratings

        int id = Integer.parseInt(request.getParameter("id"));
        CV cv = cvDAO.getById(id);

        // Fetch all skills and the skills the mentor has selected
        List<Skill> allSkills = skillDAO.getSkillWithParam(null);
        List<Integer> mentorSkills = skillDAO.getMentorSkills(cv.getMentor().getId());

        // Fetch comments (ratings) for this mentor
        List<Rating> ratings = ratingDAO.getRatingsByMentorId(cv.getMentor().getId());

        request.setAttribute("cv", cv);
        request.setAttribute("title", "CV Detail - "+cv.getMentor().getUser().getFullname());
        request.setAttribute("mentor", cv.getMentor());
        request.setAttribute("skills", allSkills);
        request.setAttribute("mentorSkills", mentorSkills);

        // Pass the ratings to the JSP
        request.setAttribute("ratings", ratings);  
        System.out.println(ratings);
        request.getRequestDispatcher("cv-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
