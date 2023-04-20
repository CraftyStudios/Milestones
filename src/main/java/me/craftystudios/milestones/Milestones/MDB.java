package me.craftystudios.milestones.Milestones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import me.craftystudios.milestones.utils.Error; // YOU MUST IMPORT THE CLASS ERROR, AND ERRORS!!!
import me.craftystudios.milestones.utils.Errors;
import me.craftystudios.milestones.Main; // Import main class!


public abstract class MDB {
    Main plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "milestones";
    public int tokens = 0;
    public MDB(Main instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE milestone = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }


    public String getGoal(String milestone) {
        String query = "SELECT * FROM " + table + " WHERE milestone = ?;";
        try (Connection conn = getSQLConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, milestone);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("milestone").equalsIgnoreCase(milestone.toLowerCase())) {
                        String goal = rs.getString("goal");
                        return goal;
                    }
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return null;
    }
    

//     public Integer getMilestones(String string) {
//         Connection conn = null;
//         PreparedStatement ps = null;
//         ResultSet rs = null;
//         try {
//             conn = getSQLConnection();
//             ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE milestone = '"+string+"';");
   
//             rs = ps.executeQuery();
//             while(rs.next()){
//                 if(rs.getString("milestone").equalsIgnoreCase(string.toLowerCase())){
//                     return rs.getInt("total");
//                 }
//             }
//         } catch (SQLException ex) {
//             plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
//         } finally {
//             try {
//                 if (ps != null)
//                     ps.close();
//                 if (conn != null)
//                     conn.close();
//             } catch (SQLException ex) {
//                 plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
//             }
//         }
//         return 0;
//     }

// Now we need methods to save things to the database
    public void setMilestoneGoal(String milestone, String completeMethod) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (milestone,complete) VALUES(?,?)"); // IMPORTANT. In SQLite class, We made 3 colums. milestone, Kills, Total.
            ps.setString(1, milestone.toLowerCase());                                             // YOU MUST put these into this line!! And depending on how many
                                                                                                         // colums you put (say you made 5) All 5 need to be in the brackets
                                                                                                         // Seperated with comma's (,) AND there needs to be the same amount of
                                                                                                         // question marks in the VALUES brackets. Right now i only have 3 colums
                                                                                                         // So VALUES (?,?,?) If you had 5 colums VALUES(?,?,?,?,?)
                                                                                               
            ps.setString(2, completeMethod); // This sets the value in the database. The colums go in order. milestone is ID 1, kills is ID 2, Total would be 3 and so on. you can use
                                  // setInt, setString and so on. tokens and total are just variables sent in, You can manually send values in as well. p.setInt(2, 10) <-
                                  // This would set the milestones kills instantly to 10. Sorry about the variable names, It sets their kills to 10 i just have the variable called
                                  // Tokens from another plugin :/
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;      
    }


    public void newMilestone(String milestoneName) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + table + " (milestone, complete) VALUES (?, ?)");
            ps.setString(1, milestoneName.toLowerCase());
            ps.setString(2, "incomplete");
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
    }
    


    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}
 
