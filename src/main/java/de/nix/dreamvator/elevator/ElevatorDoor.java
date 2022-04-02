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

import java.util.List;

public class ElevatorDoor {

    private List<Block> blocks;
    private List<FallingBlock> fallingBlocks;
    private BlockFace direction;

    private int time;

    private Plugin plugin;

    public ElevatorDoor(Plugin plugin, Location loc1, Location loc2, BlockFace direction) {
        this.plugin = plugin;
        this.direction = direction;

        Cuboid cuboid = new Cuboid(loc1, loc2);
        this.blocks = cuboid.getBlocks();
    }

    private void move(int timeInTicks) {
        if(blocks == null || blocks.isEmpty())
            return;
        this.time = time;
        blocks.forEach(block -> {
            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
            fallingBlock.setGravity(false);
            fallingBlock.setInvulnerable(true);
            fallingBlock.setTicksLived(-1);
            fallingBlock.setDropItem(false);

            fallingBlocks.add(fallingBlock);
            block.setType(Material.AIR);
        });

        Bukkit.getScheduler().runTaskTimer(plugin, new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                if(ticks < time) {
                    fallingBlocks.forEach(fallingBlock -> {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ticks + " " + fallingBlock.getBlockData().getMaterial()));
                        });
                    });
                }
                this.cancel();
            }
        }, 1, 1);

    }

}
