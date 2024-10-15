/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.SkillDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Skill;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SkillListController", urlPatterns = {"/skill-list"})
public class SkillListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchParam = request.getParameter("search");
        String pageParam = request.getParameter("page");
        SkillDAO skillDAO = new SkillDAO();
        int page = 1; // Default to the first page
        int pageSize = 4; // Set the desired page size
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }
        List<Skill> skills = skillDAO.getSkillWithParam(searchParam, Status.ACTIVE.name());
        List<Skill> pagingSkill = skillDAO.Paging(skills, page, pageSize);
        System.out.println(pagingSkill);

        request.setAttribute("skills", pagingSkill);
        request.setAttribute("search", searchParam);
        request.setAttribute("totalPages", skills.size() % pageSize == 0 ? (skills.size() / pageSize) : (skills.size() / pageSize + 1));
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("skill-list.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
