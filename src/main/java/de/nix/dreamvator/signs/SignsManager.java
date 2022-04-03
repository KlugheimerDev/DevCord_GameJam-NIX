package de.nix.dreamvator.signs;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class SignsManager implements Listener {

    /*
        Verschiedene Schilderarten

        "[lock]" - Macht Türen nicht mehr aufmachbar
        "[checkpoint]" "level" - Setzt Checkpoint mit der Nummer "level"
        "[doorWhat]" "sync/switch" "x,y,z" - Wenn eine Tür mit dem Schild aufgeht, gehen die andere auch auf
        "[mapEnd]" "" "1/2"
     */


    private Plugin plugin;

    public SignsManager(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDoorRightClick(PlayerInteractEvent event) {
        if(!event.hasBlock() || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType().toString().contains("TRAP") || !event.getClickedBlock().getType().toString().contains("DOOR")) return;
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

    String diamondPressed = "";
    String goldPressed = "";
    @EventHandler
    public void onMove2(PlayerMoveEvent event) {
        if(!event.getPlayer().getLocation().add(0, -1, 0).getBlock().getType().equals(Material.BONE_BLOCK)) {
            if(!diamondPressed.equalsIgnoreCase("") || !goldPressed.equalsIgnoreCase(""))
                if(diamondPressed.equalsIgnoreCase(event.getPlayer().getDisplayName())) goldPressed = "";
                if(goldPressed.equalsIgnoreCase(event.getPlayer().getDisplayName())) goldPressed = "";
        }

        Location signLoc = event.getPlayer().getLocation().add(0, -2, 0);
        if(!signHasString(signLoc, "[lasergun]")) return;

        if(getLineToString(signLoc, 1).equalsIgnoreCase("golden")) goldPressed = event.getPlayer().getDisplayName();
        if(getLineToString(signLoc, 1).equalsIgnoreCase("diamond")) diamondPressed = event.getPlayer().getDisplayName();

        if(!diamondPressed.equalsIgnoreCase("") && !goldPressed.equalsIgnoreCase("")) {
            diamondPressed = "";
            goldPressed = "";

            ItemStack dHoe = new ItemStack(Material.DIAMOND_AXE);
            ItemMeta itemMeta = dHoe.getItemMeta();
            itemMeta.setDisplayName("§1Lasergun");
            dHoe.setItemMeta(itemMeta);
            Dreamvator.getPlayers().get(0).getInventory().setItem(1, dHoe);

            ItemStack hoe = new ItemStack(Material.DIAMOND_AXE);
            ItemMeta itemMetaD = hoe.getItemMeta();
            itemMetaD.setDisplayName("§6Pfeilspucker");
            hoe.setItemMeta(itemMetaD);
            Dreamvator.getPlayers().get(1).getInventory().setItem(1, hoe);
        }
    }

    String onePressed = "";
    String secondPressed = "";
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(!event.getPlayer().getLocation().add(0, -1, 0).getBlock().getType().equals(Material.EMERALD_BLOCK)) {
            if(!onePressed.equalsIgnoreCase("") || !secondPressed.equalsIgnoreCase(""))
                if(onePressed.equalsIgnoreCase(event.getPlayer().getDisplayName())) onePressed = "";
                if(secondPressed.equalsIgnoreCase(event.getPlayer().getDisplayName())) secondPressed = "";
        }

        Location signLoc = event.getPlayer().getLocation().add(0, -2, 0);
        if(!signHasString(signLoc, "[mapEnd]")) return;

        if(getLineToString(signLoc, 2).equalsIgnoreCase("1")) onePressed = event.getPlayer().getDisplayName();
        if(getLineToString(signLoc, 2).equalsIgnoreCase("2")) secondPressed = event.getPlayer().getDisplayName();

        if(!onePressed.equalsIgnoreCase("") && !secondPressed.equalsIgnoreCase("")) {
            onePressed = "";
            secondPressed = "";

            Dreamvator.stageManager.switchToNextStage();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().teleport(Dreamvator.checkpointManager.getCheckPoint(event.getEntity()));
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(Dreamvator.checkpointManager.getCheckPoint(e.getPlayer()));
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

    private String getLineToString(Location location, int i) {
        Block signBlock = location.getBlock();
        if(signBlock.getType().toString().contains("SIGN")) {
            Sign sign = (Sign) signBlock.getState();
            return sign.getLine(i);
        }
        return "";
    }
}
