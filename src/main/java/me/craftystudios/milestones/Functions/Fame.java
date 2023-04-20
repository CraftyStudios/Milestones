package me.craftystudios.milestones.Functions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.craftystudios.milestones.Main;
import me.craftystudios.milestones.Fame.FDB;
import me.craftystudios.milestones.Fame.FameDB;
import me.craftystudios.milestones.Constants.FameRank;

public class Fame {
    private Main plugin;
    private FDB db;
    public Fame(Main plugin) {
        this.db = new FameDB(plugin);
        this.db.load();
    }
    FameRank fame = new FameRank(plugin); 


    public void addFame(String player, int amount) {

    }
    public void checkAndUpdateFameLevel(Player player) {
        int currentFame = db.getFameLevel(player); // get the player's current fame level
        int requiredFame = fame.fameRequirement(currentFame + 1); // get the required fame for the next level
        int playerFame = db.getFame(player); // get the player's current fame
        int fameInt = (int) db.getFameLevel(player); // get the player's fame level before the update (for comparison4
        int beforeFame = fameInt - 1; // get the player's fame level before the update (for comparison)
        if (playerFame >= requiredFame) {
            db.setFameLevel(player, currentFame + 1); // update the player's fame level
            player.sendMessage("&b&l----------------------\n&b&lFAME LEVEL UP &r&1" + beforeFame + " → " + currentFame + "\n\n&r&a&lREWARDS\n  &eFame Rank " + currentFame + "\nrewards\n&r&b&l----------------------"); // send a message to the player
        }
    
    new BukkitRunnable() {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                int currentFame = db.getFameLevel(player);
                int requiredFame = fame.fameRequirement(currentFame + 1);
                int playerFame = db.getFameLevel(player);
    
                if (playerFame >= requiredFame) {
                    db.setFameLevel(player, currentFame + 1);
                    player.sendMessage("Congratulations! You have reached fame level " + (currentFame + 1) + "!");
                }
            }
        }
    }.runTaskTimerAsynchronously(plugin, 0L, 20L); // run every hour
}

}