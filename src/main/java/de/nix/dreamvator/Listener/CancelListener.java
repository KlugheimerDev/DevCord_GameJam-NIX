package de.nix.dreamvator.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(disabledBlocks.contains(event.getClickedBlock().getType()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage3(EntityDamageByBlockEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setFormat("§7" + e.getPlayer().getName() + "8: §f" + e.getMessage());
    }

}
