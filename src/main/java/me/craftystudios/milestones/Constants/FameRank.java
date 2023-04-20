package me.craftystudios.milestones.Constants;

import me.craftystudios.milestones.Main;

public class FameRank {
    private Main plugin;

    public FameRank(Main plugin) {
        this.plugin = plugin;
    }

    public int fameRequirement(int level) {
        int fameRequirement;
        float multiplier = plugin.getConfig().getInt("fame-multiplier");
        fameRequirement = (int) (Math.pow(level, multiplier) * 100);
        return fameRequirement;
    }
    
}
