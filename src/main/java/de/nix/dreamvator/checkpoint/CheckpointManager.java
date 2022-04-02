package de.nix.dreamvator.checkpoint;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CheckpointManager implements Listener {
    //Checkpoint-metaData Aufbau:  "checkpoint"  "checkpointNummer,x,y,z"

    public void setPlayersCheckpoint(Player player, int checkpointLevel) {
        String value = checkpointLevel + "," + player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ();

        if(!player.hasMetadata("checkpoint"))
            Dreamvator.metadataManager.setMetadata(player, "checkpoint", value);

        else if(Integer.parseInt(player.getMetadata("checkpoint").get(0).asString().substring(0, 1)) < checkpointLevel)
            Dreamvator.metadataManager.setMetadata(player, "checkpoint", value);
    }

    public void teleportToRespawnLocation(Player player) {
        player.teleport(getPlayersRespawnLocation(player));
    }

    public void removePlayerMetaData(Player player) {
        Dreamvator.metadataManager.removeMetadata(player,"checkpoint");
    }

    public Location getPlayersRespawnLocation(Player player) {
        if(!player.hasMetadata("checkpoint")) return player.getLocation();
        String[] metaStrings = player.getMetadata("checkpoint").get(0).asString().split(",");

        return new Location(player.getWorld(), Double.parseDouble(metaStrings[1]), Double.parseDouble(metaStrings[2]), Double.parseDouble(metaStrings[3]));
    }
}
