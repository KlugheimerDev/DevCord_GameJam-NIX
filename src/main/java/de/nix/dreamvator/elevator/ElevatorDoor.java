package de.nix.dreamvator.elevator;

import de.nix.dreamvator.misc.Cuboid;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

import java.util.List;

public class ElevatorDoor {

    private List<Block> blocks;
    private BlockFace direction;

    public ElevatorDoor(Location loc1, Location loc2, BlockFace direction) {
        this.direction = direction;

        Cuboid cuboid = new Cuboid(loc1, loc2);
        this.blocks = cuboid.getBlocks();
    }

}
