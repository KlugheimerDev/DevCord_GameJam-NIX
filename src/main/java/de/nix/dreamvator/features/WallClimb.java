package de.nix.dreamvator.features;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class WallClimb {

    private Plugin plugin;

    public WallClimb(Plugin plugin) {
        this.plugin = plugin;

        start();
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if(Dreamvator.getPlayers() == null || Dreamvator.getPlayers().isEmpty())
                return;
            Dreamvator.getPlayers().forEach(player -> {
                if(isPlayerOnWall(player)) {
                    player.setVelocity(player.getVelocity().setY(0.25));
                }
            });
        }, 0, 1);
    }

    public boolean isPlayerOnWall(Player player) {
        if(player.getLocation().add(1, 0, 0).getBlock().getType().equals(Material.WAXED_OXIDIZED_CUT_COPPER) ||
                player.getLocation().add(-1, 0, 0).getBlock().getType().equals(Material.WAXED_OXIDIZED_CUT_COPPER) ||
                player.getLocation().add(0, 0, 1).getBlock().getType().equals(Material.WAXED_OXIDIZED_CUT_COPPER) ||
                player.getLocation().add(0, 0, -1).getBlock().getType().equals(Material.WAXED_OXIDIZED_CUT_COPPER)) {
            return true;
        }

        return false;
    }
}
