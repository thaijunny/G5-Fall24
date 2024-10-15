/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CVDAO;
import dal.RequestDAO;
import dal.SkillDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.CV;
import model.Request;
import model.Skill;
import model.User;
import model.enums.Role;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateRequestController", urlPatterns = {"/update-request"})
public class UpdateRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null && user.getRole().equals(Role.MENTEE)) {
            int id = Integer.parseInt(request.getParameter("id"));
            RequestDAO requestDAO = new RequestDAO();
            Request myRequest = requestDAO.getByID(id);
            CVDAO cvdao = new CVDAO();
            CV cv = cvdao.getByMentorId(myRequest.getMentor().getId());
            if (cv == null) {
                session.setAttribute("notificationErr", "This mentor not have CV");
                response.sendRedirect("create-request");
                return;
            } else {
                request.setAttribute("price", cv.getPrice());
            }
            request.setAttribute("myRequest", myRequest);
            request.setAttribute("title", "Update request");
            SkillDAO skillDAO = new SkillDAO();
            List<Skill> skills = skillDAO.getMentorSkill(myRequest.getMentor().getId());

            request.setAttribute("skills", skills);
            request.getRequestDispatcher("update-request.jsp").forward(request, response);
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
        SkillDAO skillDAO = new SkillDAO();
        if (user != null && user.getRole().equals(Role.MENTEE)) {
            int requestId = Integer.parseInt(request.getParameter("id"));
            RequestDAO requestDAO = new RequestDAO();
            Request existingRequest = requestDAO.getByID(requestId);

            // Get form parameters
            String subject = request.getParameter("subject");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String content = request.getParameter("content");
            String skillId = request.getParameter("skillId");

            // Combine date and time into a single deadline string
            // Validation logic (not shown here, but similar to the previous example)
            boolean hasErrors = false;
            // Validate and set attributes for errors, if necessary

            if (hasErrors) {
                request.setAttribute("myRequest", existingRequest);
                request.getRequestDispatcher("update-request.jsp").forward(request, response);
                return;
            }

            // Update request with new details
            existingRequest.setSubject(subject);
            if (startDateStr != null && !startDateStr.isEmpty()) {
                java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);  // Convert String to Date
                existingRequest.setStartDate(startDate);
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);  // Convert String to Date
                existingRequest.setEndDate(endDate);
            }
            existingRequest.setContent(content);
            existingRequest.setSkill(skillDAO.getSkillById(Integer.parseInt(skillId)));

            boolean success = requestDAO.update(existingRequest);

            if (success) {
                session.setAttribute("notification", "Request updated successfully.");
                response.sendRedirect("my-request");
            } else {
                session.setAttribute("notificationErr", "Failed to update the request.");
                response.sendRedirect("update-request?id=" + requestId);
            }
        } else {
            session.setAttribute("notificationErr", "You must login first!");
            response.sendRedirect("login");
        }
    }
}
