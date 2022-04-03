package de.nix.dreamvator.features;

import de.nix.dreamvator.Dreamvator;
import de.nix.dreamvator.stage.StageChangeEvent;
import de.nix.dreamvator.stage.StageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
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
        if(event.getStage().getID() == 3) {
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
            } else {
                diamondOwner = ((Player) event.getEntity());
            }
        } else if(itm == getGoldenGun()) {
            if(((Player) event.getEntity()).getInventory().contains(getDiamondGun())) {
                event.setCancelled(true);
                return;
            } else {
                goldOwner = ((Player) event.getEntity());
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

    Player diamondOwner;
    Player goldOwner;
    boolean firstShot;
    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if(event.getAction() != Action.LEFT_CLICK_AIR || event.getAction() != Action.LEFT_CLICK_BLOCK)  return;
        if(event.getItem() == null || !Dreamvator.getPlayers().contains(event.getPlayer())) return;
        if(!event.getItem().getType().toString().contains("AXE")) return;
        event.setCancelled(true);

        if(event.getItem().getType() == Material.DIAMOND_AXE) {
            shoot(goldOwner);
        } else if(event.getItem().getType() == Material.GOLDEN_AXE) {
            shoot(diamondOwner);
        }

        if(!firstShot) {
            firstShot = true;
            //hier irgendein Kommentar einbauen vom andern Spieler
        }
    }

    private ItemStack getDiamondGun() {
        ItemStack hoe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta itemMeta = hoe.getItemMeta();
        itemMeta.setDisplayName("§1Pfeilspucker");

        hoe.setItemMeta(itemMeta);
        return hoe;
    }

    private ItemStack getGoldenGun() {
        ItemStack hoe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta itemMeta = hoe.getItemMeta();
        itemMeta.setDisplayName("§6Pfeilspucker");

        hoe.setItemMeta(itemMeta);
        return hoe;
    }

    private void shoot(Player player) {
        Arrow arrow = (Arrow) player.launchProjectile(Arrow.class);
        arrow.setVelocity(arrow.getVelocity().multiply(1));
        arrow.setGravity(false);
        arrow.setSilent(true);
    }
}
