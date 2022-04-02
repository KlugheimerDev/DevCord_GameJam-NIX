package de.nix.dreamvator.elevator;

import de.nix.dreamvator.Dreamvator;
import de.nix.dreamvator.misc.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    private Plugin plugin;

    private int time;

    private List<Block> blocks;
    private List<ElevatorDoor> doors;

    private Cuboid cuboid;

    public Elevator(Plugin plugin, Location position1, Location position2) {
        this.plugin = plugin;
        this.cuboid = new Cuboid(position1, position2);

        List<Block> cuboidBlocks = cuboid.getBlocks();
        List<Block> newBlocks = new ArrayList<>();
        cuboidBlocks.forEach(block -> {
            if(!block.getType().equals(Material.LIGHT_GRAY_TERRACOTTA))
                newBlocks.add(block);
        });
        this.blocks = newBlocks;
    }

    public Elevator addDoor(ElevatorDoor door) {
        if(doors == null)
            doors = new ArrayList<>(2);
        doors.add(door);
        return this;
    }

    public List<ElevatorDoor> getDoors() {
        return doors;
    }
}
