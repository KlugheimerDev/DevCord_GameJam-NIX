package de.nix.dreamvator.features;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class LaserGun implements Listener {

    private final Plugin plugin;

    private boolean onePickedUp = false;

    World world = Bukkit.getWorld("world");
    ArrayList<Block> blockList = new ArrayList<>();


    public LaserGun(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        blockList.add(world.getBlockAt(getLocation(69,-62,-16)));
        blockList.add(world.getBlockAt(getLocation(65,-60,-13)));
        blockList.add(world.getBlockAt(getLocation(68,-57,-13)));
        blockList.add(world.getBlockAt(68, -62, -11));
        blockList.add(world.getBlockAt(65,-58,-11));
        blockList.add(world.getBlockAt(66, -63, -8));
        blockList.add(world.getBlockAt(67, -56, -8));
    }

    boolean firstShot = false;

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(!event.getItem().getType().toString().contains("AXE")) return;
            if(event.getItem().getType() == Material.DIAMOND_AXE) {
                shoot(Dreamvator.getPlayers().get(1));
            } else if(event.getItem().getType() == Material.GOLDEN_AXE) {
                shoot(Dreamvator.getPlayers().get(0));
            }

            firstShot = true;
        }
    }

    @EventHandler
    public void onProjectilHit(ProjectileHitEvent event) {
        if(!(event.getEntity() instanceof Arrow)) return;

        if(event.getHitBlock() != null && blockList.contains(event.getHitBlock())) {
            blockList.remove(event.getHitBlock());
            event.getHitBlock().setType(Material.DIAMOND_BLOCK);

            event.getEntity().remove();

            if(blockList.size() == 0) {
                world.getBlockAt(70,-59,-26).setType(Material.AIR);
                world.getBlockAt(70,-59,-27).setType(Material.AIR);
                world.getBlockAt(70,-58,-26).setType(Material.AIR);
                world.getBlockAt(70,-58,-27).setType(Material.AIR);
            }
        }
    }

    private Location getLocation(int i, int i1, int i2) {
        return new Location(Bukkit.getWorld("world"), i, i1, i2);
    }

    private void shoot(Player player) {
        Arrow arrow = (Arrow) player.launchProjectile(Arrow.class);
        arrow.setVelocity(arrow.getVelocity().multiply(1));
        arrow.setGravity(false);
        arrow.setSilent(true);

        if(!firstShot)
            Bukkit.broadcastMessage("§7" + player.getName() + "§8: §fWoher kam der Schuss ?");
    }
}
