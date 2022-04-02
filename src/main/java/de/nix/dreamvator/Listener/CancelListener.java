package de.nix.dreamvator.Listener;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class CancelListener implements Listener {

    private Dreamvator plugin;

    public CancelListener(Dreamvator plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }
    /*
    private List<Material> disabledBlocks = Arrays.asList(Material.FLOWER_POT);

    public void onTopf(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(disabledBlocks.contains(event.getClickedBlock().getType()))
            event.setCancelled(true);
    }
     */


}
