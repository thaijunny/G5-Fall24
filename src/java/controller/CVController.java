package controller;

import dal.MentorDAO;
import dal.CVDAO;
import dal.SkillDAO;
import model.Mentor;
import model.User;
import model.enums.Role;
import model.CV;
import model.Skill;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.List;
import model.enums.CVStatus;

@WebServlet(name = "CVController", urlPatterns = {"/my-cv"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class CVController extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "image";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        MentorDAO mentorDAO = new MentorDAO();
        CVDAO cvDAO = new CVDAO();
        SkillDAO skillDAO = new SkillDAO();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            session.setAttribute("notificationErr", "You must login first");
            response.sendRedirect("login");
            return;
        } else if (!user.getRole().equals(Role.MENTOR)) {
            session.setAttribute("notificationErr", "You must be a mentor to access this page.");
            response.sendRedirect("login");
            return;
        }

        Mentor mentor = mentorDAO.getByUserId(user.getId());
        CV cv = cvDAO.getByMentorId(mentor.getId());

        // Fetch all skills and the skills the mentor has selected
        List<Skill> allSkills = skillDAO.getSkillWithParam(null);
        List<Integer> mentorSkills = skillDAO.getMentorSkills(mentor.getId());

        request.setAttribute("cvExists", cv != null);
        request.setAttribute("cv", cv);
        if (cv != null) {
            request.setAttribute("cvStatus", cv.getStatus());
        }
        request.setAttribute("mentor", mentor);
        request.setAttribute("title", "My CV");
        request.setAttribute("skills", allSkills);
        request.setAttribute("mentorSkills", mentorSkills);  // Send the mentor's skills list

        request.getRequestDispatcher("my-cv.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        MentorDAO mentorDAO = new MentorDAO();
        CVDAO cvDAO = new CVDAO();
        SkillDAO skillDAO = new SkillDAO();

        if (user == null) {
            session.setAttribute("notificationErr", "You must login first");
            response.sendRedirect("login");
            return;
        } else if (!user.getRole().equals(Role.MENTOR)) {
            session.setAttribute("notificationErr", "You must be a mentor to create or update a CV.");
            response.sendRedirect("login");
            return;
        }

        Mentor mentor = mentorDAO.getByUserId(user.getId());

        // Handle avatar upload
        Part avatarPart = request.getPart("avatar");
        if (avatarPart != null && avatarPart.getSize() > 0) {
            String fileName = avatarPart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + fileName;
            avatarPart.write(filePath);

            // Save avatar path to mentor
            mentor.setAvatar(fileName);
            mentorDAO.updateMentor(mentor);  // Save avatar to mentor table
        }

        // Extract form fields from the request
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String[] skillIds = request.getParameterValues("skillIds");

        CV existingCV = cvDAO.getByMentorId(mentor.getId());

        if (existingCV == null) {
            // Create a new CV
            CV newCV = new CV();
            newCV.setMentor(mentor);
            newCV.setDescription(description);
            newCV.setPrice(price);
            newCV.setStatus(CVStatus.PENDING);

            // Insert into the database
            boolean isInserted = cvDAO.insertCV(newCV);
            if (isInserted) {
                session.setAttribute("notification", "CV created successfully! Please wait the Manager approve");
            } else {
                session.setAttribute("notificationErr", "Failed to create CV.");
            }
        } else {
            // Update existing CV
            existingCV.setDescription(description);
            existingCV.setPrice(price);
            existingCV.setStatus(CVStatus.PENDING);
            // Update in the database
            boolean isUpdated = cvDAO.updateCV(existingCV);
            if (isUpdated) {
                session.setAttribute("notification", "CV updated successfully! Please wait the Manager approve");
            } else {
                session.setAttribute("notificationErr", "Failed to update CV.");
            }
        }

        // Update mentor skills
        skillDAO.updateMentorSkills(mentor.getId(), skillIds);

        response.sendRedirect("my-cv");
    }
}
