package de.nix.dreamvator;

import de.nix.dreamvator.Listener.CancelListener;
import de.nix.dreamvator.cameras.CameraManager;
import de.nix.dreamvator.checkpoint.CheckpointManager;
import de.nix.dreamvator.elevator.ElevatorManager;
import de.nix.dreamvator.features.WallClimb;
import de.nix.dreamvator.misc.MetadataManager;
import de.nix.dreamvator.signs.SignsManager;
import de.nix.dreamvator.stage.LobbyManager;
import de.nix.dreamvator.stage.StageManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Dreamvator extends JavaPlugin {

    private Dreamvator instance;

    public static MetadataManager metadataManager;
    public static CheckpointManager checkpointManager;
    public static CameraManager cameraManager;
    public static ElevatorManager elevatorManager;

    public static StageManager stageManager;
    private static List<Player> players;

    public static final String PREFIX = "§9" + ChatColor.of("#3972CB") + "Dreamvator §7» ";

    @Override
    public void onEnable() {
        this.instance = this;

        this.players = new ArrayList<>();

        SignsManager signsManager = new SignsManager(this);
        elevatorManager = new ElevatorManager(this);
        LobbyManager lobbyManager = new LobbyManager(this);
        cameraManager = new CameraManager(this);
        WallClimb wallClimb = new WallClimb(this);

        CancelListener cancelListener = new CancelListener(this);

        stageManager = new StageManager(this);
        metadataManager = new MetadataManager(this);
        checkpointManager = new CheckpointManager(this);

        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDer Aufzug wurde gestartet");
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§cINFO: Die Main-World MUSS 'world' heißen, ansonsten ist die Map nicht spielbar");
    }

    @Override
    public void onDisable() {
        cameraManager.disable();
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§cNotstopp aktiviert");
    }

    public Dreamvator getInstance() {
        return instance;
    }

    public MetadataManager getMetadataManager() {
        return metadataManager;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static ElevatorManager getElevatorManager() {
        return elevatorManager;
    }
}
