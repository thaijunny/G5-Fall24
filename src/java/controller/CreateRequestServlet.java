/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CVDAO;
import dal.MenteeDAO;
import dal.MentorDAO;
import dal.MentorScheduleDAO;
import dal.MentorSkillDAO;
import dal.RequestDAO;
import dal.SkillDAO;
import dal.WalletDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import model.CV;
import model.Mentee;
import model.Mentor;
import model.MentorSchedule;
import model.Request;
import model.Skill;
import model.User;
import model.Wallet;
import model.enums.RequestStatus;
import model.enums.Role;

@WebServlet("/create-request")
public class CreateRequestServlet extends HttpServlet {

    final SkillDAO skillDAO = new SkillDAO();
    final MentorDAO mentorDAO = new MentorDAO();
    final MenteeDAO menteeDAO = new MenteeDAO();
    final MentorScheduleDAO mentorScheduleDAO = new MentorScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user != null && user.getRole().equals(Role.MENTEE)) {
            String skillIdParam = request.getParameter("sid");
            String mentorIdParam = request.getParameter("mid");
            if (skillIdParam != null) {
                int skillId = Integer.parseInt(skillIdParam);
                Skill selectedSkill = skillDAO.getSkillById(skillId);
                request.setAttribute("selectedSkill", selectedSkill);
            }

            // Check if mentorId is provided and fetch the corresponding mentor
            if (mentorIdParam != null) {
                int mentorId = Integer.parseInt(mentorIdParam);
                Mentor selectedMentor = mentorDAO.getById(mentorId);
                CVDAO cvdao = new CVDAO();
                CV cv = cvdao.getByMentorId(mentorId);
                if (cv == null) {
                    session.setAttribute("notificationErr", "This mentor not have CV");
                    response.sendRedirect("create-request");
                    return;
                } else {
                    request.setAttribute("price", cv.getPrice());
                }
                List<MentorSchedule> listMentorSchedules = mentorScheduleDAO.getMentorSchedules(mentorId);

                request.setAttribute("listMentorSchedules", listMentorSchedules);
                request.setAttribute("selectedMentor", selectedMentor);
            }
            request.setAttribute("title", "Create Request");

            List<Mentor> mentors = mentorDAO.getAllMentors();
            List<Skill> skills = skillDAO.getSkillWithParam(null);
            request.setAttribute("skills", skills);
            request.setAttribute("mentors", mentors);
            request.getRequestDispatcher("create-request.jsp").forward(request, response);
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

        if (user != null && user.getRole().equals(Role.MENTEE)) {
            // Get form parameters
            String subject = request.getParameter("subject");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String content = request.getParameter("content");
            String skillId = request.getParameter("skillId");
            String mentorId = request.getParameter("mentorId");
            String status = request.getParameter("status");
            String price = request.getParameter("totalPrice");

            // Retrieve selected slot IDs
            String[] slotIds = request.getParameterValues("slotIds");

            // Create a new request object
            Request newRequest = new Request();
            newRequest.setSubject(subject);
            if (startDateStr != null && !startDateStr.isEmpty()) {
                java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);  // Convert String to Date
                newRequest.setStartDate(startDate);
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);  // Convert String to Date
                newRequest.setEndDate(endDate);
            }
            newRequest.setMentee(menteeDAO.getByUserId(user.getId()));
            newRequest.setContent(content);
            newRequest.setPrice(price);
            newRequest.setSkill(skillDAO.getSkillById(Integer.parseInt(skillId)));
            newRequest.setMentor(mentorDAO.getById(Integer.parseInt(mentorId)));
            newRequest.setStatus(RequestStatus.valueOf(status.toUpperCase()));
            newRequest.setCreateDate(new Date(System.currentTimeMillis()));

            // Check mentor's price and validate wallet balance
            Mentor selectedMentor = mentorDAO.getById(Integer.parseInt(mentorId));
            CVDAO cvdao = new CVDAO();
            CV cv = cvdao.getByMentorId(selectedMentor.getId());
            if (cv == null) {
                session.setAttribute("notificationErr", "This mentor does not have a CV");
                response.sendRedirect("create-request");
                return;
            } else {
                request.setAttribute("price", cv.getPrice());
            }

            Double myAmount = (Double) session.getAttribute("myAmount");
            if (myAmount == null || myAmount < cv.getPrice()) {
                session.setAttribute("notificationErr", "Your balance is insufficient! Please recharge and try again.");
                response.sendRedirect("create-request");
            } else {
                // Save the request
                RequestDAO requestDAO = new RequestDAO();
                boolean success = requestDAO.saveRequest(newRequest);

                if (success) {
                    // Deduct amount from wallet and save the selected slots
                    WalletDAO walletDAO = new WalletDAO();
                    Mentee mentee = menteeDAO.getByUserId(user.getId());
                    Wallet wallet = walletDAO.getWalletByMenteeId(mentee.getId());
                    wallet.setAmount(myAmount - cv.getPrice());
                    walletDAO.update(wallet);

                    // Insert selected slots into request_schedule
                    Request latestRequest = requestDAO.getLastesRequest();
                    if (slotIds != null) {
                        for (String slotId : slotIds) {
                            int slotID = Integer.parseInt(slotId);
                            requestDAO.insertRequestSchedule(latestRequest.getRequestId(), slotID);
                        }
                    }

                    session.setAttribute("myAmount", myAmount - cv.getPrice());
                    session.setAttribute("account", user);
                    request.getSession().setAttribute("notification", "Request created successfully.");
                    response.sendRedirect("my-request");
                } else {
                    session.setAttribute("notificationErr", "Failed to create the request. Please try again.");
                    response.sendRedirect("create-request");
                }
            }
        } else {
            session.setAttribute("notificationErr", "You must log in first!");
            response.sendRedirect("login");
        }
    }

}
