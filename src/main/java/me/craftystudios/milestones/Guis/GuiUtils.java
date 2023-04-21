package me.craftystudios.milestones.Guis;

import net.wesjd.anvilgui.AnvilGUI;

public class GuiUtils {
//creating commit message for identifying bug quickly
    public String milestoneNameInput(){
        new AnvilGUI.Builder()
            .onClose( player -> {
                player.sendMessage("Cancelled");
            }
            .onComplete((completion) ->
            )
        );

    }
    
}
