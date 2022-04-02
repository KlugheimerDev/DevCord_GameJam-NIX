package de.nix.dreamvator.cameras;

import de.nix.dreamvator.Dreamvator;
import de.nix.dreamvator.misc.Skull;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.stream.Collectors;

public class Camera {

    private Plugin plugin;

    private Location location;
    private ArmorStand armorStand;

    public Camera(Plugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;

        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.armorStand.setHelmet(Skull.getCustomSkull("http://textures.minecraft.net/texture/3db83586542934f8c3231a5284f2489b87678478454fca69359447569f157d14", ""));
        this.armorStand.setInvisible(true);
        this.armorStand.setGravity(false);
        this.armorStand.setCustomName("camera");
        this.armorStand.setMarker(true);
        this.armorStand.setInvulnerable(true);
    }

    private Player getNearestPlayer() {
        Player nearestPlayer = null;

        for(Player all : Dreamvator.getPlayers()) {
            if(nearestPlayer == null) nearestPlayer = all;
            else if(all.getLocation().distance(location) < nearestPlayer.getLocation().distance(location)) nearestPlayer = all;
        }

        return nearestPlayer;
    }

}
