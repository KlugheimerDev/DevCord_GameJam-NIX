package de.nix.dreamvator.cameras;

import de.nix.dreamvator.Dreamvator;
import de.nix.dreamvator.misc.Skull;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import java.util.stream.Collectors;

public class Camera {

    private Plugin plugin;

    private Location location;
    private ArmorStand armorStand;

    public Camera(Plugin plugin, Location location, CameraType type) {
        this.plugin = plugin;
        this.location = location.add(0.5, 0, 0.5);

        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        if(type.equals(CameraType.CAMERA)) {
            this.armorStand.setHelmet(Skull.getCustomSkull("http://textures.minecraft.net/texture/3db83586542934f8c3231a5284f2489b87678478454fca69359447569f157d14", ""));
            this.armorStand.setInvisible(true);
        }else {
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
            bootsMeta.setColor(Color.WHITE);boots.setItemMeta(bootsMeta);

            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
            leggingsMeta.setColor(Color.WHITE);leggings.setItemMeta(leggingsMeta);

            ItemStack chestplate = new ItemStack(Material.LEATHER_BOOTS);LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
            chestplateMeta.setColor(Color.WHITE);chestplate.setItemMeta(chestplateMeta);

            this.armorStand.setHelmet(Skull.getCustomSkull("http://textures.minecraft.net/texture/3db83586542934f8c3231a5284f2489b87678478454fca69359447569f157d14", ""));
            this.armorStand.setChestplate(chestplate);
            this.armorStand.setLeggings(leggings);
            this.armorStand.setBoots(boots);
        }

        this.armorStand.setGravity(false);
        this.armorStand.setCustomName("camera");
        this.armorStand.setMarker(true);
        this.armorStand.setInvulnerable(true);
    }

    public Player getNearestPlayer() {
        Player nearestPlayer = null;

        for(Player all : Bukkit.getOnlinePlayers()) {
            if(nearestPlayer == null) {
                nearestPlayer = all;
            }else if(all.getLocation().distance(location) < nearestPlayer.getLocation().distance(location)) {
                nearestPlayer = all;
            }
        }

        return nearestPlayer;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Location getLocation() {
        return location;
    }
}
