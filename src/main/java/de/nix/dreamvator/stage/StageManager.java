package de.nix.dreamvator.stage;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StageManager {

    //Bett 1: -14, -59, -61
    //Bett 2: -13, -59, -61

    private Dreamvator plugin;
    private Stage currentStage;

    public StageManager(Dreamvator plugin) {
        this.plugin = plugin;
        currentStage = Stage.STAGE1;
    }

    public void switchToNextStage() {
        throwEffect();
    }

    public void setCurrentStage(int id) {
        Dreamvator.getPlayers().get(0).teleport(new Location(Dreamvator.getPlayers().get(0).getWorld(),-14, -58, -61));
        Dreamvator.getPlayers().get(1).teleport(new Location(Dreamvator.getPlayers().get(1).getWorld(),-13, -58, -61));

        Stage[] stages = Stage.values();
        currentStage = stages[id];

        Bukkit.broadcastMessage("Currenct Stage" + currentStage.getID());
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    private void throwEffect() {
        Dreamvator.getPlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1, false, false, false));
            player.setVelocity(BlockFace.EAST.getDirection().multiply(2).setY(0.2));
        });

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                setCurrentStage(currentStage.getID()+1);
            }
        }, 20);
    }

    public enum Stage{
        STAGE1(0),
        STAGE2(1);

        private final int id;

        Stage(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }
}
