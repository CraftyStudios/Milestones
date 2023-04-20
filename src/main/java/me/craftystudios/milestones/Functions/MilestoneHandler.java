package me.craftystudios.milestones.Functions;

import me.craftystudios.milestones.Main;
import me.craftystudios.milestones.Milestones.MDB;
import me.craftystudios.milestones.Milestones.MilestoneDB;

public class MilestoneHandler {
    private Main plugin;
    private MDB db;
    public MilestoneHandler(Main plugin) {
        this.plugin = plugin;
        this.db = new MilestoneDB(plugin);
    }
    public String goal(String milestoneName) {
        return db.getGoal(milestoneName);
    }
    
}