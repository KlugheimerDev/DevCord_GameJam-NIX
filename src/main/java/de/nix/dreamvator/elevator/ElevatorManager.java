package de.nix.dreamvator.elevator;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ElevatorManager implements Listener {

    private Plugin plugin;
    private List<Elevator> elevators;

    public ElevatorManager(Plugin plugin) {
        this.plugin = plugin;
        this.elevators = new ArrayList<>();

        registerElevators();
    }

    private void registerElevators() {
        elevators.add(new Elevator(plugin, new Location(Bukkit.getWorld("world"), 46, -57, -98), new Location(Bukkit.getWorld("world"), 51, -49, -93))
                 .addDoor(new ElevatorDoor(plugin, new Location(Bukkit.getWorld("world"), 51, -49, -93), new Location(Bukkit.getWorld("world"), 51, -49, -93), BlockFace.SOUTH)));
        Bukkit.getConsoleSender().sendMessage(Dreamvator.PREFIX + "§aAufzüge wurden registriert");
    }

    private void dispatch(Elevator elevator) {
        elevator.getDoors().get(0).move(20);
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        if(e.getItemDrop().getItemStack().getType().equals(Material.SCUTE)) {
            if(elevators != null && !elevators.isEmpty()) {
                dispatch(elevators.get(0));
            }
        }
        if(e.getItemDrop().getItemStack().getType().equals(Material.REDSTONE_TORCH)) {
            if(elevators != null && !elevators.isEmpty()) {
                elevators.get(0).getDoors().get(0).reset();
            }
        }
    }

}
