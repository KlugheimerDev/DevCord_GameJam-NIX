package de.nix.dreamvator.cameras;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class CameraManager {

    private Plugin plugin;
    private List<Camera> cameras;

    public CameraManager(Plugin plugin) {
        this.plugin = plugin;
        this.cameras = new ArrayList<>();

        registerCameras();

        start();
    }

    public void registerCameras() {
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),-13, -59, 38), CameraType.SCIENTIST));
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),-1, -36.5, -19), CameraType.CAMERA));
    }

    public void disable() {
        if(cameras == null || cameras.isEmpty())
            return;
        cameras.forEach(camera -> {
            camera.getArmorStand().remove();
        });
        cameras.clear();
    }

    public void start() {
        if(cameras == null || cameras.isEmpty())
            return;
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for(Camera camera : cameras) {
                    ArmorStand armorStand = camera.getArmorStand();
                    if(camera.getNearestPlayer() != null) {
                        Player player = camera.getNearestPlayer();

                        Location direction = player.getLocation().subtract(camera.getLocation());
                        armorStand.setHeadPose(directionToEuler(direction));
                    }
                }
            }
        }, 1, 1);
    }

    /*
        Euler-Method | Thanks to CoKoC
     */
    private EulerAngle directionToEuler(Location dir) {
        double xzLength = Math.sqrt(dir.getX()*dir.getX() + dir.getZ()*dir.getZ());
        double pitch = Math.atan2(xzLength, dir.getY()) - Math.PI / 2;
        double yaw = -Math.atan2(dir.getX(), dir.getZ()) + Math.PI / 180;
        return new EulerAngle(pitch, yaw, 0);
    }
}
