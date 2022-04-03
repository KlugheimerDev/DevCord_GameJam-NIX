package de.nix.dreamvator.stage;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StageManager {

    //Bett 1: -14, -59, -61

    private Dreamvator plugin;
    private Stage currentStage;

    private boolean stageSwitch = false;

    public StageManager(Dreamvator plugin) {
        this.plugin = plugin;
        currentStage = Stage.LOBBY;
    }

    public void switchToNextStage() {
        throwEffect();
    }

    public void setCurrentStage(int id) {
        if(stageSwitch) return;
        stageSwitch = true;
        Dreamvator.getPlayers().get(0).teleport(new Location(Dreamvator.getPlayers().get(0).getWorld(),currentStage.x1, -58, -61, -70, 68));
        Dreamvator.getPlayers().get(1).teleport(new Location(Dreamvator.getPlayers().get(1).getWorld(),currentStage.x2, -58, -61, 55, 68));

        for(int i = 0; i < 6; i++)
            Dreamvator.getPlayers().get(0).getWorld().playSound(new Location(Dreamvator.getPlayers().get(0).getWorld() ,-15, -59, -60), Sound.BLOCK_BELL_USE, 1, 1);

        Stage[] stages = Stage.values();
        currentStage = stages[id];

        Bukkit.broadcastMessage("currentStage :" + currentStage.getID());

        StageChangeEvent event = new StageChangeEvent(currentStage);
        Bukkit.getPluginManager().callEvent(event);
        stageSwitch = false;
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

    public Stage getCurrentStage() {
        return currentStage;
    }

    public enum Stage{
        LOBBY(0, -13.5, -12.5),
        STAGE1(1, -13.5, -12.5),
        STAGE2(2, -53.5, -52.5),
        STAGE3(3, -92.5, -91.5),
        STAGE4(4, 83.5, 84.5),
        STAGE5(5, 33, 34),
        ENDE(6, -13.5, 12.5);

        private final int id;
        private final double x1;
        private final double x2;

        Stage(int id, double x1, double x2) {
            this.id = id;
            this.x1 = x1;
            this.x2 = x2;
        }

        public int getID() {
            return id;
        }
    }
}
