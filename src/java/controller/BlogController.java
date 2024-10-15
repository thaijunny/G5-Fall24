/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BlogDAO;
import dal.RequestDAO;
import dal.SkillDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Blog;
import model.Request;
import model.Skill;
import model.User;
import model.enums.Role;
import model.enums.Status;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BlogController", urlPatterns = {"/blog"})
public class BlogController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
//        if (user != null) {
            String searchParam = request.getParameter("search");
            String pageParam = request.getParameter("page");
            BlogDAO blogDAO = new BlogDAO();
            int page = 1; // Default to the first page
            int pageSize = 6; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            List<Blog> blogs = blogDAO.getAllBlogWithParam(searchParam);
            List<Blog> pagingBlog = blogDAO.Paging(blogs, page, pageSize);

            request.setAttribute("blogs", pagingBlog);
            request.setAttribute("title", "Blog");
            request.setAttribute("search", searchParam);
            request.setAttribute("totalPages", blogs.size() % pageSize == 0 ? (blogs.size() / pageSize) : (blogs.size() / pageSize + 1));
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("blog.jsp").forward(request, response);
//        } else {
//            session.setAttribute("notificationErr", "You must login first!");
//            response.sendRedirect("login");
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null && user.getRole().equals(Role.MENTEE)) {

        } else {
            session.setAttribute("notificationErr", "You must login first!");
            response.sendRedirect("login");
        }
    }

}
