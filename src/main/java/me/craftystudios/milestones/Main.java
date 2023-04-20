package me.craftystudios.milestones;

import org.bukkit.plugin.java.JavaPlugin;

import me.craftystudios.milestones.Fame.FDB;
import me.craftystudios.milestones.Fame.FameDB;
import me.craftystudios.milestones.Milestones.MDB;
import me.craftystudios.milestones.Milestones.MilestoneDB;
import me.craftystudios.milestones.Playerdata.PDB;
import me.craftystudios.milestones.Playerdata.PlayerdataDB;
import me.craftystudios.milestones.utils.*;

public final class Main extends JavaPlugin {
    private PDB pdb;
    private FDB fdb;
    private MDB mdb;

    @Override
    public void onEnable() {
      this.fdb = new FameDB(this);
      this.fdb.load();
      this.mdb = new MilestoneDB(this);
      this.mdb.load();
      this.pdb = new PlayerdataDB(this);
      this.pdb.load();
      Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
      Logger.log(Logger.LogLevel.SUCCESS, "Loading Milestones...");
      Logger.log(Logger.LogLevel.SUCCESS, "Loaded!");
      Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
    }   

    @Override
    public void onDisable() {
      Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
      Logger.log(Logger.LogLevel.SUCCESS, "Unloading Milestones...");
      Logger.log(Logger.LogLevel.SUCCESS, "Unloaded!");
      Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
      this.saveConfig();
      }

      public FDB getFDBRDatabase() {
        return this.fdb;
      }
      public MDB getMDBRDatabase() {
        return this.mdb;
      }
      public PDB getPDBRDatabase() {
        return this.pdb;
    }
}