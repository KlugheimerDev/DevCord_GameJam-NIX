package de.nix.dreamvator.elevator;

import de.nix.dreamvator.misc.Cuboid;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.FallingBlock;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ElevatorDoor {

    private List<Block> blocks;
    private List<FallingBlock> fallingBlocks;
    private BlockFace direction;

    private Material doorBlock;

    private int time;

    private Plugin plugin;

    public ElevatorDoor(Plugin plugin, Location loc1, Location loc2, BlockFace direction) {
        this.plugin = plugin;
        this.direction = direction;

        this.doorBlock = Material.TARGET;

        Cuboid cuboid = new Cuboid(loc1, loc2);
        this.blocks = cuboid.getBlocks();
        this.fallingBlocks = new ArrayList<>();
    }

    public void reset() {
        if(fallingBlocks != null && !fallingBlocks.isEmpty()) {
            fallingBlocks.forEach(fallingBlock -> {
                fallingBlock.remove();
            });
            blocks.forEach(block -> {
                block.setType(doorBlock);
            });
        }
    }

    public void move(boolean open, int timeInTicks) {
        if(blocks == null || blocks.isEmpty())
            return;
        this.time = time;
        blocks.forEach(block -> {
            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
            fallingBlock.setGravity(false);
            fallingBlock.setInvulnerable(true);
            fallingBlock.setTicksLived(Integer.MAX_VALUE);
            fallingBlock.setDropItem(false);

            fallingBlocks.add(fallingBlock);
            block.setType(Material.AIR);
        });

        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                if(ticks < time) {
                    fallingBlocks.forEach(fallingBlock -> {
                        //move
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ticks + " " + fallingBlock.getBlockData().getMaterial()));
                        });
                    });
                }
                this.cancel();
            }
        }.runTaskTimer(plugin, 1, 1);

    }

}
