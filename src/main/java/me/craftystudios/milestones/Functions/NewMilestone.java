package me.craftystudios.milestones.Functions;

import me.craftystudios.milestones.*;
import me.craftystudios.milestones.Milestones.MDB;
import me.craftystudios.milestones.Milestones.MilestoneDB;
public class NewMilestone{
    private MDB db;
    public NewMilestone(Main plugin){
        this.db = new MilestoneDB(plugin);
        this.db.load();
    }
    public void createMilestone(String milestoneName, String milestoneGoal) {
        db.newMilestone(milestoneName);
        db.setMilestoneGoal(milestoneName, milestoneGoal);
    }
}