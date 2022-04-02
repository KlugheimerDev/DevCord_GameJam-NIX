package de.nix.dreamvator.Listener;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class CancelListener implements Listener {

    private Plugin plugin;

    public CancelListener(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private List<Material> disabledBlocks = Arrays.asList(Material.FLOWER_POT, Material.LEVER);

    public void onInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(disabledBlocks.contains(event.getClickedBlock().getType()))
            event.setCancelled(true);
    }

    public void onPlace(BlockPlaceEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            e.setCancelled(true);
        }
    }

}
