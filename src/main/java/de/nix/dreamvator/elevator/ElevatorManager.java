package de.nix.dreamvator.elevator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ElevatorManager {

    private Plugin plugin;
    private List<Elevator> elevators;

    public ElevatorManager(Plugin plugin) {
        this.plugin = plugin;
        registerElevators();
    }

    private void registerElevators() {
        elevators.add(new Elevator(plugin, new Location(Bukkit.getWorld("world"), 46, -57, -98), new Location(Bukkit.getWorld("world"), 51, -49, -93))
                 .addDoor(new ElevatorDoor(plugin, new Location(Bukkit.getWorld("world"), 51, -49, -93), new Location(Bukkit.getWorld("world"), 51, -49, -93), BlockFace.SOUTH)));
    }

    private void dispatch(Elevator elevator) {

    }

}
