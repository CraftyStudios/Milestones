package me.craftystudios.milestones.Playerdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import me.craftystudios.milestones.utils.Error; // YOU MUST IMPORT THE CLASS ERROR, AND ERRORS!!!
import me.craftystudios.milestones.utils.Errors;
import me.craftystudios.milestones.Main; // Import main class!


public abstract class PDB {
    Main plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "playerdata";
    public int tokens = 0;
    public PDB(Main instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }


    public Integer getMilestoneCount(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '"+string+"';");
   
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())){ // Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                    return rs.getInt("milestonecount"); 
                }
            }
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
        return 0;
    }    

    // public Integer getMilestones(String string) {
    //     Connection conn = null;
    //     PreparedStatement ps = null;
    //     ResultSet rs = null;
    //     try {
    //         conn = getSQLConnection();
    //         ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '"+string+"';");
   
    //         rs = ps.executeQuery();
    //         while(rs.next()){
    //             if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())){
    //                 return rs.getInt("total");
    //             }
    //         }
    //     } catch (SQLException ex) {
    //         plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
    //     } finally {
    //         try {
    //             if (ps != null)
    //                 ps.close();
    //             if (conn != null)
    //                 conn.close();
    //         } catch (SQLException ex) {
    //             plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
    //         }
    //     }
    //     return 0;
    // }

// Now we need methods to save things to the database
    public void setMilestoneCount(Player player, Integer milestoneCount) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,milestoneCount) VALUES(?,?)"); // IMPORTANT. In SQLite class, We made 3 colums. player, Kills, Total.
            ps.setString(1, player.getName().toLowerCase());                                             // YOU MUST put these into this line!! And depending on how many
                                                                                                         // colums you put (say you made 5) All 5 need to be in the brackets
                                                                                                         // Seperated with comma's (,) AND there needs to be the same amount of
                                                                                                         // question marks in the VALUES brackets. Right now i only have 3 colums
                                                                                                         // So VALUES (?,?,?) If you had 5 colums VALUES(?,?,?,?,?)
                                                                                               
            ps.setInt(2, milestoneCount); // This sets the value in the database. The colums go in order. Player is ID 1, kills is ID 2, Total would be 3 and so on. you can use
                                  // setInt, setString and so on. tokens and total are just variables sent in, You can manually send values in as well. p.setInt(2, 10) <-
                                  // This would set the players kills instantly to 10. Sorry about the variable names, It sets their kills to 10 i just have the variable called
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
 
