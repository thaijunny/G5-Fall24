/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.SkillDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import model.Skill;
import model.User;
import model.enums.Role;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SkillManagement", urlPatterns = {"/admin/manage-skill"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class SkillManagement extends HttpServlet {

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
            String pageParam = request.getParameter("page");
            SkillDAO skillDAO = new SkillDAO();
            int page = 1; // Default to the first page
            int pageSize = 6; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            List<Skill> skills = skillDAO.getSkillWithParam(searchParam, statusParam);
            List<Skill> pagingSkill = skillDAO.Paging(skills, page, pageSize);

            request.setAttribute("skills", pagingSkill);
            request.setAttribute("search", searchParam);
            request.setAttribute("totalPages", skills.size() % pageSize == 0 ? (skills.size() / pageSize) : (skills.size() / pageSize + 1));
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("skill-management.jsp").forward(request, response);
        }

    }

    private static final String IMAGE_DIRECTORY = "image";  // Folder where images are saved

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SkillDAO skillDAO = new SkillDAO();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("notificationErr", "You musr login first");
            response.sendRedirect("../login");

        } else if (!user.getRole().equals(Role.ADMIN)) {
            response.sendRedirect("../login");

        } else {
            String action = request.getParameter("action");
            switch (action) {
                case "add" -> {
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    Part imagePart = request.getPart("image");

                    // Get the file name and the path to save the file
                    String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                    String uploadPath = getServletContext().getRealPath("") + File.separator + IMAGE_DIRECTORY;

                    // Ensure the directory exists
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    // Save the file on the server
                    String filePath = uploadPath + File.separator + fileName;
                    imagePart.write(filePath);
                    System.out.println(fileName);
                    // Save Skill to the database

                    Skill skill = new Skill();
                    skill.setName(name);
                    skill.setDescription(description);
                    skill.setImage(fileName);
                    skill.setStatus(Status.ACTIVE);

                    skillDAO.addSkill(skill);
                    session.setAttribute("notification", "Skill added successfully !");
                    response.sendRedirect("manage-skill");
                }
                case "activate" -> {
                    int skillId = Integer.parseInt(request.getParameter("skillId"));
                    Skill skill = skillDAO.getSkillById(skillId);
                    skill.setStatus(Status.ACTIVE);
                    skillDAO.updateStatus(skill);

                    session.setAttribute("notification", "Skill activated successfully!");
                    response.sendRedirect("manage-skill");
                }
                case "deactivate" -> {
                    int skillId = Integer.parseInt(request.getParameter("skillId"));
                    Skill skill = skillDAO.getSkillById(skillId);
                    skill.setStatus(Status.INACTIVE);
                    System.out.println(skill);
                    skillDAO.updateStatus(skill);
                    
                    session.setAttribute("notification", "Skill deactivated successfully!");
                    response.sendRedirect("manage-skill");
                }
                case "update" -> {
                    int skillId = Integer.parseInt(request.getParameter("skillId"));
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    Part imagePart = request.getPart("image");

                    Skill existingSkill = skillDAO.getSkillById(skillId);
                    String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

                    // Check if the user has uploaded a new image
                    if (!fileName.isEmpty()) {
                        // Get the path to save the file
                        String uploadPath = getServletContext().getRealPath("") + File.separator + IMAGE_DIRECTORY;

                        // Ensure the directory exists
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdir();
                        }

                        // Save the file on the server
                        String filePath = uploadPath + File.separator + fileName;
                        imagePart.write(filePath);

                        // Set the new image filename in the skill object
                        existingSkill.setImage(fileName);
                    }

                    // Update other fields
                    existingSkill.setName(name);
                    existingSkill.setDescription(description);

                    // Update Skill in the database
                    skillDAO.updateSkill(existingSkill);

                    session.setAttribute("notification", "Skill updated successfully!");
                    response.sendRedirect("manage-skill");
                }

                default -> {
                }
            }

        }
    }

}
