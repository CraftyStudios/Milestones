package me.craftystudios.milestones.Fame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import me.craftystudios.milestones.utils.Error; // YOU MUST IMPORT THE CLASS ERROR, AND ERRORS!!!
import me.craftystudios.milestones.utils.Errors;
import me.craftystudios.milestones.Main; // Import main class!


public abstract class FDB {
    Main plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "fame";
    public int tokens = 0;
    public FDB(Main instance){
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


    public Integer getFame(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = ?;");
            ps.setString(1, player.getName());
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(player.getName().toLowerCase())){ 
                    return rs.getInt("fame"); 
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
    
    public int getFameLevel(Player player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = ?;");
            ps.setString(1, player.getUniqueId().toString());
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(player.getUniqueId().toString())){ // Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                    return rs.getInt("famelevel"); 
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
    
    // public Integer get(String string) {
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
    public void setFame(Player player, Integer fame) {
        Connection conn = null;
        PreparedStatement ps = null;
        UUID uuid = player.getUniqueId();
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,fame,famelevel) VALUES(?,?,?)"); // IMPORTANT. In SQLite class, We made 3 colums. player, Kills, Total.
            ps.setString(1, uuid.toString());                                             // YOU MUST put these into this line!! And depending on how many
                                                                                                         // colums you put (say you made 5) All 5 need to be in the brackets
                                                                                                         // Seperated with comma's (,) AND there needs to be the same amount of
                                                                                                         // question marks in the VALUES brackets. Right now i only have 3 colums
                                                                                                         // So VALUES (?,?,?) If you had 5 colums VALUES(?,?,?,?,?)
                                                                                               
            ps.setInt(2, fame); // This sets the value in the database. The colums go in order. Player is ID 1, kills is ID 2, Total would be 3 and so on. you can use
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

    public void setFameLevel(Player player, Integer famelevel) {
        Connection conn = null;
        PreparedStatement ps = null;
        UUID uuid = player.getUniqueId();
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,fame,famelevel) VALUES(?,?,?)"); // IMPORTANT. In SQLite class, We made 3 colums. player, Kills, Total.
            ps.setString(1, uuid.toString());                                             // YOU MUST put these into this line!! And depending on how many
                                                                                                         // colums you put (say you made 5) All 5 need to be in the brackets
                                                                                                         // Seperated with comma's (,) AND there needs to be the same amount of
                                                                                                         // question marks in the VALUES brackets. Right now i only have 3 colums
                                                                                                         // So VALUES (?,?,?) If you had 5 colums VALUES(?,?,?,?,?)
                                                                                               
            ps.setInt(3, famelevel); // This sets the value in the database. The colums go in order. Player is ID 1, kills is ID 2, Total would be 3 and so on. you can use
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
 
