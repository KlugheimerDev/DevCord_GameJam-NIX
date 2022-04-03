package de.nix.dreamvator.features;

import de.nix.dreamvator.Dreamvator;
import de.nix.dreamvator.stage.StageChangeEvent;
import de.nix.dreamvator.stage.StageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class LaserGun implements Listener {

    private final Plugin plugin;

    private boolean onePickedUp = false;

    public LaserGun(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onStageChange(StageChangeEvent event) {
        if(event.getStage() == StageManager.Stage.STAGE3) {
            Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), -25, -59, 10), getDiamondGun());
            Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), -25, -59, 13), getGoldenGun());

            Bukkit.getWorld("world").getBlockAt(-22, -60, 11).setType(Material.TINTED_GLASS);
            Bukkit.getWorld("world").getBlockAt(-22, -60, 12).setType(Material.TINTED_GLASS);
            Bukkit.getWorld("world").getBlockAt(-22, -59, 11).setType(Material.TINTED_GLASS);
            Bukkit.getWorld("world").getBlockAt(-22, -59, 12).setType(Material.TINTED_GLASS);
        }
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        ItemStack itm = event.getItem().getItemStack();
        if(itm == getDiamondGun()) {
            if(((Player) event.getEntity()).getInventory().contains(getGoldenGun())) {
                event.setCancelled(true);
                return;
            }
        } else if(itm == getGoldenGun()) {
            if(((Player) event.getEntity()).getInventory().contains(getDiamondGun())) {
                event.setCancelled(true);
                return;
            }
        }

        if(!onePickedUp) {
            onePickedUp = true;
        } else if(onePickedUp) {
            Bukkit.getWorld("world").getBlockAt(-22, -60, 11).setType(Material.AIR);
            Bukkit.getWorld("world").getBlockAt(-22, -60, 12).setType(Material.AIR);
            Bukkit.getWorld("world").getBlockAt(-22, -59, 11).setType(Material.AIR);
            Bukkit.getWorld("world").getBlockAt(-22, -59, 12).setType(Material.AIR);
            Bukkit.getWorld("world").playSound(new Location( Bukkit.getWorld("world"), -22, -59, 12), Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, 1, 1);
        }
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if(event.getAction() != Action.LEFT_CLICK_AIR || event.getAction() != Action.LEFT_CLICK_BLOCK)  return;
        if(event.getItem() == null || !Dreamvator.getPlayers().contains(event.getPlayer())) return;
        if(!event.getItem().getType().toString().contains("HOE")) return;
        event.setCancelled(true);

        if(event.getItem().getType() == Material.DIAMOND_HOE) {

        }
    }

    private ItemStack getDiamondGun() {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta itemMeta = hoe.getItemMeta();
        itemMeta.setDisplayName("§1Lasergun");

        hoe.setItemMeta(itemMeta);
        return hoe;
    }

    private ItemStack getGoldenGun() {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta itemMeta = hoe.getItemMeta();
        itemMeta.setDisplayName("§6Lasergun");

        hoe.setItemMeta(itemMeta);
        return hoe;
    }
}
