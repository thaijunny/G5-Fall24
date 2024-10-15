/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MentorSkillDAO;
import dal.SkillDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.MentorSkill;
import model.Skill;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SkillDetailController", urlPatterns = {"/skill-detail"})
public class SkillDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SkillDAO skillDAO = new SkillDAO();
        MentorSkillDAO msdao = new MentorSkillDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        String searchParam = request.getParameter("search");
        String pageParam = request.getParameter("page");
        Skill skill = skillDAO.getSkillById(id);
        request.setAttribute("skill", skill);
        request.setAttribute("title", "Skill Detail - " + skill.getName());
        
        int page = 1; // Default to the first page
        int pageSize = 4; // Set the desired page size
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }
        List<MentorSkill> mentorSkills = msdao.getCVOfSkillsWithParam(id, searchParam);
        List<MentorSkill> pagingSkill = msdao.Paging(mentorSkills, page, pageSize);
        List<Skill> skills = skillDAO.otherSkill(id);
        request.setAttribute("top10Skill", skills);
        request.setAttribute("skills", pagingSkill);
        request.setAttribute("title", "Skill List");
        request.setAttribute("search", searchParam);
        request.setAttribute("totalPages", mentorSkills.size() % pageSize == 0 ? (mentorSkills.size() / pageSize) : (mentorSkills.size() / pageSize + 1));
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("skill-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
