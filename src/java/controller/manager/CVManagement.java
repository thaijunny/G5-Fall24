/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.CVDAO;
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
import model.CV;
import model.Skill;
import model.User;
import model.enums.CVStatus;
import model.enums.Role;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CVManagement", urlPatterns = {"/manager/manage-cv"})

public class CVManagement extends HttpServlet {

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
            CVDAO cvdao = new CVDAO();
            int page = 1; // Default to the first page
            int pageSize = 6; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            List<CV> skills = cvdao.getCVsWithParam(searchParam, statusParam);
            List<CV> pagingCVs = cvdao.Paging(skills, page, pageSize);

            request.setAttribute("cv", pagingCVs);
            request.setAttribute("search", searchParam);
            request.setAttribute("totalPages", skills.size() % pageSize == 0 ? (skills.size() / pageSize) : (skills.size() / pageSize + 1));
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("cv-management.jsp").forward(request, response);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        CVDAO cvdao = new CVDAO();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("notificationErr", "You musr login first");
            response.sendRedirect("../login");

        } else if (!user.getRole().equals(Role.MANAGER)) {
            response.sendRedirect("../login");

        } else {
            String action = request.getParameter("action");
            switch (action) {
               
                case "approve" -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    CV cv = cvdao.getById(id);
                    cv.setStatus(CVStatus.APPROVED);
                    cvdao.updateCV(cv);

                    session.setAttribute("notification", "CV approve successfully!");
                    response.sendRedirect("manage-cv");
                }
                case "reject" -> {
                     int id = Integer.parseInt(request.getParameter("id"));
                    CV cv = cvdao.getById(id);
                    cv.setStatus(CVStatus.REJECTED);
                    System.out.println(cv);
                    cvdao.updateCV(cv);
                    
                    session.setAttribute("notification", "CV reject successfully!");
                    response.sendRedirect("manage-cv");
                }
               
                default -> {
                }
            }

        }
    }

}
