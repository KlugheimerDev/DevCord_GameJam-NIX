package de.nix.dreamvator.stage;

import de.nix.dreamvator.Dreamvator;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StageManager {

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
        Dreamvator.getPlayers().get(0).teleport(new Location(Dreamvator.getPlayers().get(0).getWorld(),currentStage.x1, -58, -61, -70, 68));
        Dreamvator.getPlayers().get(1).teleport(new Location(Dreamvator.getPlayers().get(1).getWorld(),currentStage.x2, -58, -61, 55, 68));

        for(int i = 0; i < 6; i++)
            Dreamvator.getPlayers().get(0).getWorld().playSound(new Location(Dreamvator.getPlayers().get(0).getWorld() ,-15, -59, -60), Sound.BLOCK_BELL_USE, 1, 1);

        Stage[] stages = Stage.values();
        currentStage = stages[id];

        StageChangeEvent event = new StageChangeEvent(currentStage);
        Bukkit.getPluginManager().callEvent(event);
        stageSwitch = false;

        if(currentStage.getID() == 2)
            Bukkit.broadcastMessage("§7" + Dreamvator.getPlayers().get(0).getDisplayName() + "§7 » §r§fWaren wir nicht hier schonmal?");

        if(currentStage.getID() == 3) {
            closeDoor(-20, -60, 41);
            closeDoor(-20, -60, 44);
            closeDoor(-16, -60, 44);
            closeDoor(-11, -60, 44);
            closeDoor(-11, -60, 41);
            closeDoor(-5, -60, 43);
            closeDoor(-5, -60, 42);
            closeDoor(-6, -60, 46);
            closeDoor(-9, -60, 46);
            closeDoor(-6, -60, 51);
            closeDoor(-9, -60, 51);
            closeDoor(-4, -60, 49);
            closeDoor(-4, -60, 55);
            closeDoor(-9, -60, 58);
            closeDoor(-11, -60, 55);
            closeDoor(-7, -60, 62);
            closeDoor(-8, -60, 62);
            closeDoor(-7, -60, 65);

            World world = Bukkit.getWorld("world");
            Door door = (Door) world.getBlockAt(-6, -60, 58).getBlockData();
            door.setOpen(true);
            world.getBlockAt(-6, -60, 58).setBlockData(door);
        }

        if(currentStage.getID() == 4) {
            World world = Bukkit.getWorld("world");

            world.getBlockAt(70,-59,-26).setType(Material.GRAY_CONCRETE);
            world.getBlockAt(70,-59,-27).setType(Material.GRAY_CONCRETE);
            world.getBlockAt(70,-58,-26).setType(Material.WHITE_CONCRETE);
            world.getBlockAt(70,-58,-27).setType(Material.WHITE_CONCRETE);

            world.getBlockAt(69,-62,-16).setType(Material.TARGET);
            world.getBlockAt(65,-60,-13).setType(Material.TARGET);
            world.getBlockAt(68,-57,-13).setType(Material.TARGET);
            world.getBlockAt(68, -62, -11).setType(Material.TARGET);
            world.getBlockAt(65,-58,-11).setType(Material.TARGET);
            world.getBlockAt(66, -63, -8).setType(Material.TARGET);
            world.getBlockAt(67, -56, -8).setType(Material.TARGET);
        }

        if(currentStage.getID() == 5) {
            Dreamvator.getPlayers().forEach(player -> {
                player.getInventory().clear(1);
            });
        }

        if(currentStage.getID() == 6) {
            Bukkit.broadcastMessage(Dreamvator.PREFIX + "§7Danke für's spielen unserer Map! Das Team §dNiX §7und §dMistics §7danken für diesen schönen Contest, auch wenn wir in den letzten Minuten noch Panik hatten!");
            restart(15);
        }
    }

    public void restart(int time) {
        Bukkit.broadcastMessage("\n" + Dreamvator.PREFIX + "§7Der Traum startet in §9" + time + " Sekunden§7 neu.§r\n");

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.kickPlayer(Dreamvator.PREFIX + "§7Traum wird neu geladen..");
                });
                Bukkit.shutdown();
            }
        }, 20*time);
    }

    private void closeDoor(int x, int y, int z) {
        World world = Bukkit.getWorld("world");
        Door door = (Door) world.getBlockAt(x, y ,z).getBlockData();
        door.setOpen(false);
        world.getBlockAt(x, y, z).setBlockData(door);
    }

    private void throwEffect() {
        if(stageSwitch) return;
        stageSwitch = true;
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
        LOBBY(0, -13.5, -12.5,  new Location(Bukkit.getWorld("world"), -6.052, -40, -22.089)),
        STAGE1(1, -13.5, -12.5,  new Location(Bukkit.getWorld("world"), -6.052, -40, -22.089)),
        STAGE2(2, -53.5, -52.5,  new Location(Bukkit.getWorld("world"), -24, -41, 88.980)),
        STAGE3(3, -92.5, -91.5,  new Location(Bukkit.getWorld("world"), -28.150, -60, 42.972)),
        STAGE4(4, 83.5, 84.5,  new Location(Bukkit.getWorld("world"), 67.052, -59, -39)),
        STAGE5(5, 33, 34,  new Location(Bukkit.getWorld("world"), -6.052, -40, -22.089)),
        ENDE(6, -13.5, 12.5,  new Location(Bukkit.getWorld("world"), -6.052, -40, -22.089));

        private final int id;
        private final double x1;
        private final double x2;

        private final Location loc;

        Stage(int id, double x1, double x2, Location loca) {
            this.id = id;
            this.x1 = x1;
            this.x2 = x2;

            loc = loca;
        }

        public int getID() {
            return id;
        }

        public void teleportToElevator(Player player) {
            player.teleport(loc);
        }
    }
}
