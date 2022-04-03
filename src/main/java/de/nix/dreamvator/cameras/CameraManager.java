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
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),6, -37, -30), CameraType.SCIENTIST));

        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),-1, -38.5, -19), CameraType.CAMERA));
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),-86, -57.5, -69), CameraType.CAMERA));
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),83, -57.5, -89), CameraType.CAMERA));
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),64, -57.5, -34), CameraType.CAMERA));
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),69, -56.5, -29), CameraType.CAMERA));
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),-25, -38.5, 105), CameraType.CAMERA));
        this.cameras.add(new Camera(plugin, new Location(Bukkit.getWorld("world"),-39, -36.5, 99), CameraType.CAMERA));

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
        }, 0, 1);
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
