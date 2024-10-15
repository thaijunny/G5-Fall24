package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Skill;
import model.enums.Status;

public class SkillDAO extends DBContext {

    public List<Skill> getTop10Skill() {
        List<Skill> skills = new ArrayList<>();
        // Declare Connection here
        Connection con = null;
        try {
            con = DBContext.getConnection();
            String query = """
                        SELECT * FROM Skill WHERE status = ? ORDER BY SkillID LIMIT 10
                         """;
            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, Status.ACTIVE.name());
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Skill s = new Skill();
                    s.setId(rs.getInt("SkillID"));
                    s.setName(rs.getString("SkillName"));
                    s.setImage(rs.getString("Image"));
                    s.setDescription(rs.getString("Description"));
                    String statusString = rs.getString("status");
                    if (statusString != null) {
                        s.setStatus(Status.valueOf(statusString.toUpperCase()));
                    }
                    skills.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }
    
    public List<Skill> otherSkill(int id) {
        List<Skill> skills = new ArrayList<>();
        // Declare Connection here
        Connection con = null;
        try {
            con = DBContext.getConnection();
            String query = """
                        SELECT * FROM Skill WHERE status = ? AND NOT skillID = ? ORDER BY SkillID LIMIT 10
                         """;
            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, Status.ACTIVE.name());
            preparedStatement.setInt(2, id);
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Skill s = new Skill();
                    s.setId(rs.getInt("SkillID"));
                    s.setName(rs.getString("SkillName"));
                    s.setImage(rs.getString("Image"));
                    s.setDescription(rs.getString("Description"));
                    String statusString = rs.getString("status");
                    if (statusString != null) {
                        s.setStatus(Status.valueOf(statusString.toUpperCase()));
                    }
                    skills.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public List<Skill> getSkillWithParam(String searchParam) {
        List<Skill> skills = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM Skill Where status = ? ");
            PreparedStatement preparedStatement;
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND  SkillName Like ? ");
                list.add("%" + searchParam + "%");
            }
            query.append(" ORDER BY SkillName ");
            preparedStatement = con.prepareStatement(query.toString());
            preparedStatement.setString(1, Status.ACTIVE.name());
            mapParams(preparedStatement, list);
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Skill s = new Skill();
                    s.setId(rs.getInt("SkillID"));
                    s.setName(rs.getString("SkillName"));
                    s.setImage(rs.getString("Image"));
                    s.setDescription(rs.getString("Description"));
                    String statusString = rs.getString("status");
                    if (statusString != null) {
                        s.setStatus(Status.valueOf(statusString.toUpperCase()));
                    }
                    skills.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public List<Skill> getSkillWithParam(String searchParam, String status) {
        List<Skill> skills = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM Skill Where 1 = 1 ");
            PreparedStatement preparedStatement;
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND  SkillName Like ? ");
                list.add("%" + searchParam + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                query.append(" AND  Status = ? ");
                list.add(status);
            }
            query.append(" ORDER BY SkillName ");
            preparedStatement = con.prepareStatement(query.toString());

            mapParams(preparedStatement, list);
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Skill s = new Skill();
                    s.setId(rs.getInt("SkillID"));
                    s.setName(rs.getString("SkillName"));
                    s.setImage(rs.getString("Image"));
                    s.setDescription(rs.getString("Description"));
                    String statusString = rs.getString("status");
                    if (statusString != null) {
                        s.setStatus(Status.valueOf(statusString.toUpperCase()));
                    }
                    skills.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public void addSkill(Skill skill) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBContext.getConnection();
            String query = "INSERT INTO Skill (SkillName, Description, Image, Status) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, skill.getName());
            ps.setString(2, skill.getDescription());
            ps.setString(3, skill.getImage());
            ps.setString(4, skill.getStatus().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSkill(Skill skill) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBContext.getConnection();
            String query = "UPDATE Skill SET SkillName = ?, Description = ?, Image = ? WHERE SkillID = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, skill.getName());
            ps.setString(2, skill.getDescription());
            ps.setString(3, skill.getImage()); // Image can be null or unchanged if no new image uploaded
            ps.setInt(4, skill.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(Skill skill) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBContext.getConnection();
            String query = "UPDATE Skill SET status = ? WHERE SkillID = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, skill.getStatus().name());
            ps.setInt(2, skill.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public Skill getSkillById(int skillId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Skill skill = null;

        try {
            con = DBContext.getConnection();
            String query = "SELECT * FROM Skill WHERE SkillID = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, skillId);
            rs = ps.executeQuery();

            if (rs.next()) {
                skill = new Skill();
                skill.setId(rs.getInt("SkillID"));
                skill.setName(rs.getString("SkillName"));
                skill.setDescription(rs.getString("Description"));
                skill.setImage(rs.getString("Image"));

                String statusString = rs.getString("status");
                if (statusString != null) {
                    skill.setStatus(Status.valueOf(statusString.toUpperCase()));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skill;
    }

    // Get skills selected by a mentor
    public List<Integer> getMentorSkills(int mentorId) {
        List<Integer> mentorSkills = new ArrayList<>();
        String sql = "SELECT SkillID FROM mentor_skill WHERE MentorID = ?";

        try ( Connection con = DBContext.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mentorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mentorSkills.add(rs.getInt("SkillID"));  // Add SkillID to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mentorSkills;
    }

    public List<Skill> getMentorSkill(int id) {
        List<Skill> skills = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Connection con = null;
        try {
            con = DBContext.getConnection();
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM mentor_skill Where mentorID = ? ");
            PreparedStatement preparedStatement;
            list.add(id);
            preparedStatement = con.prepareStatement(query.toString());

            mapParams(preparedStatement, list);
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Skill s = getSkillById(rs.getInt("SkillID"));
                    skills.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    // Update mentor skills by deleting existing and inserting new ones
    public void updateMentorSkills(int mentorId, String[] skillIds) {
        try ( Connection con = DBContext.getConnection()) {
            // Delete existing skills
            String deleteSQL = "DELETE FROM mentor_skill WHERE MentorID = ?";
            try ( PreparedStatement ps = con.prepareStatement(deleteSQL)) {
                ps.setInt(1, mentorId);
                ps.executeUpdate();
            }

            // Insert new skills
            if (skillIds != null && skillIds.length > 0) {
                String insertSQL = "INSERT INTO mentor_skill (MentorID, SkillID) VALUES (?, ?)";
                try ( PreparedStatement ps = con.prepareStatement(insertSQL)) {
                    for (String skillId : skillIds) {
                        ps.setInt(1, mentorId);
                        ps.setInt(2, Integer.parseInt(skillId));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Skill> Paging(List<Skill> skill, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, skill.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return skill.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        SkillDAO sdao = new SkillDAO();
        List<Skill> s = sdao.getSkillWithParam("p", Status.ACTIVE.toString());
        List<Skill> a = sdao.Paging(s, 1, 6);
        s.forEach(System.out::println);
    }
}
