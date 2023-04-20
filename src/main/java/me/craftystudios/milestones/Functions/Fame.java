package me.craftystudios.milestones.Functions;

import me.craftystudios.milestones.Main;
import me.craftystudios.milestones.Fame.FDB;
import me.craftystudios.milestones.Fame.FameDB;

public class Fame {
    private FDB db;
    public Fame(Main plugin) {
        this.db = new FameDB(plugin);
        this.db.load();
    }
    public void addFame(String player, int amount) {
    }
    

}