package de.nix.dreamvator.misc;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Iterator;

public class Cuboid implements Iterable<Block> {

    private final World world;

    protected final int x1, y1, z1;
    protected final int x2, y2, z2;

    public Cuboid(Location loc1, Location loc2) {
        if (!loc1.getWorld().equals(loc2.getWorld())) throw new IllegalArgumentException("Locations must be on the same world");
        this.world = loc1.getWorld();
        this.x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        this.y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        this.z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        this.x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        this.y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        this.z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
    }

    public ArrayList<Block> getBlocks() {
        Iterator<Block> iterator = this.iterator();
        ArrayList<Block> blocks = new ArrayList<Block>(getVolume());
        while(iterator.hasNext())
            blocks.add(iterator.next());
        return blocks;
    }

    public int getSizeX() {
        return (this.x2 - this.x1) + 1;
    }

    public int getSizeY() {
        return (this.y2 - this.y1) + 1;
    }

    public int getSizeZ() {
        return (this.z2 - this.z1) + 1;
    }

    public int getVolume() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }

    public int getHeight() {
        return Math.max(y1, y2);
    }

    public Iterator<Block> iterator() {
        return new CuboidIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    public class CuboidIterator implements Iterator<Block> {
        private World w;
        private int baseX, baseY, baseZ;
        private int x, y, z;
        private int sizeX, sizeY, sizeZ;

        public CuboidIterator(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.w = w;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            this.x = this.y = this.z = 0;
        }

        public boolean hasNext() {
            return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
        }

        public Block next() {
            Block b = this.w.getBlockAt(this.baseX + this.x, this.baseY + this.y, this.baseZ + this.z);
            if (++x >= this.sizeX) {
                this.x = 0;
                if (++this.y >= this.sizeY) {
                    this.y = 0;
                    ++this.z;
                }
            }
            return b;
        }

        public void remove() {
        }
    }

    public World getWorld() {
        return world;
    }
}
