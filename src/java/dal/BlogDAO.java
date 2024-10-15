/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author ADMIN
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import model.Mentee;
import model.Mentor;
import model.Request;
import model.Skill;
import model.User;
import model.enums.RequestStatus;

public class BlogDAO {

   

    public List<Blog> getAllBlogWithParam(String searchParam) {
        List<Blog> blogs = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        UserDAO userDAO = new UserDAO();
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append(" Select * from blog b JOIN User u ON u.userID = b.userID ");

            // Prepare the SQL statement with parameters
            PreparedStatement preparedStatement;
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND (u.fullName LIKE ? OR b.title LIKE ? ) ");
                String searchPattern = "%" + searchParam.trim() + "%";
                list.add(searchPattern);  // For fullName
                list.add(searchPattern);  // For email
            }
            

            query.append(" ORDER BY b.created_at DESC ");
            preparedStatement = con.prepareStatement(query.toString());

            // Map the parameters
            mapParams(preparedStatement, list);

            // Execute the query and process the result set
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("Blog_ID"));
                    blog.setTitle(rs.getString("title"));
                    User user = userDAO.getUserById(rs.getInt("userId"));
                    blog.setUser(user);
                    blog.setContent(rs.getString("content"));
                    blog.setCreated_at(rs.getTimestamp("created_at"));
                    blog.setImage(rs.getString("image"));
                    blogs.add(blog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

  

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }

        }
    }

    public List<Blog> Paging(List<Blog> blogs, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, blogs.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return blogs.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        BlogDAO r = new BlogDAO();
        List<Blog> list = r.getAllBlogWithParam("ok");
        for (Blog blog : list) {
            System.out.println(blog);
        }
    }

}
