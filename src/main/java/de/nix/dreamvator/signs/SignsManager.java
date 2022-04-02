package de.nix.dreamvator.signs;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class SignsManager implements Listener {

    /*
        Verschiedene Schilderarten

        "[lock]" - Macht Türen nicht mehr aufmachbar
        "[checkpoint]" "level" - Setzt Checkpoint mit der Nummer "level"
        "[doorWhat]" "sync/switch" "x,y,z" - Wenn eine Tür mit dem Schild aufgeht, gehen die andere auch auf

     */


    private Plugin plugin;

    public SignsManager(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDoorRightClick(PlayerInteractEvent event) {
        if(!event.hasBlock() || event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.getClickedBlock().getType().toString().contains("DOOR")) return;
        int checkBlock = ((Door) event.getClickedBlock().getBlockData()).getHalf().toString().contains("TOP") ? -3 : -2;

        if(signHasString(event.getClickedBlock().getLocation().add(0, checkBlock, 0), "[lock]")) {
            event.setCancelled(true);
        }

        if(signHasString(event.getClickedBlock().getLocation().add(0, checkBlock, 0), "[doorWhat]")) {
            Sign sign = (Sign) event.getClickedBlock().getLocation().add(0, checkBlock, 0).getBlock().getState();
            String[] locStrings = sign.getLine(2).split(",");
            try {
                Location loc = new Location(event.getPlayer().getWorld(), Double.parseDouble(locStrings[0]), Double.parseDouble(locStrings[1]), Double.parseDouble(locStrings[2]));
                Door otherDoor = (Door) loc.getBlock().getBlockData();
                if(sign.getLine(1).contains("sync")) {
                    otherDoor.setOpen(!((Door) event.getClickedBlock().getBlockData()).isOpen());
                } else if(sign.getLine(1).contains("switch")) {
                    otherDoor.setOpen(((Door) event.getClickedBlock().getBlockData()).isOpen());
                }

                loc.getBlock().setBlockData(otherDoor);
            } catch (Exception e) {}

        }
    }

    /*
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(signHasString(event.getPlayer().getLocation().add(0, -2, 0), "[checkpoint]")) {
            Sign sign = (Sign) event.getPlayer().getLocation().add(0, -2, 0).getBlock().getState();
            try {
                int i = Integer.parseInt(sign.getLine(1));
                Dreamvator.checkpointManager.setPlayersCheckpoint(event.getPlayer(), i);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(Dreamvator.PREFIX + "§4Checkpoint-Schild bei " + sign.getLocation().toString() + " hat keine richtige Zahl im Format 'x'");
            }
        }
    }
     */

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Dreamvator.checkpointManager.teleportToRespawnLocation(event.getEntity());
    }

    //Checkt ob ein Schild (an der location) in der ersten Zeile den String "str" besitzt
    private boolean signHasString(Location location, String str) {
        Block signBlock = location.getBlock();
        if(signBlock.getType().toString().contains("SIGN")) {
            Sign sign = (Sign) signBlock.getState();
            if(sign.getLine(0).equalsIgnoreCase(str))
                return true;
        }
        return false;
    }
}
