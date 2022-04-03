package de.nix.dreamvator.checkpoint;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class CheckpointManager implements Listener {

    private Plugin plugin;

    public CheckpointManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setCheckPoint(Player player, int checkPoint, Location location) {
        if(!player.hasMetadata("checkpoint"))
            Dreamvator.metadataManager.setMetadata(player, "checkpoint", 0);
        player.getMetadata("checkpoint").set(0, new FixedMetadataValue(plugin, checkPoint));
        player.getMetadata("checkpoint").set(1, new FixedMetadataValue(plugin, location));
    }

    public Location getCheckPoint(Player player) {
        if(player.hasMetadata("checkpoint")) {
            return (Location) player.getMetadata("checkpoint").get(1);
        }
        return new Location(player.getWorld(), -12.5, -59, -37.5, -90, 0);
    }

}
