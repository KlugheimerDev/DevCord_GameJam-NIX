package de.nix.dreamvator;

import de.nix.dreamvator.checkpoint.CheckpointManager;
import de.nix.dreamvator.elevator.ElevatorManager;
import de.nix.dreamvator.misc.MetadataManager;
import de.nix.dreamvator.signs.SignsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public final class Dreamvator extends JavaPlugin {

    private Dreamvator instance;

    public static MetadataManager metadataManager;
    public static CheckpointManager checkpointManager;

    private static List<Player> players;

    public static final String PREFIX = "§9" + ChatColor.of("#3972CB") + "Dreamvator §7» ";

    @Override
    public void onEnable() {
        this.instance = this;

        this.players = new ArrayList<>();

        SignsManager signsManager = new SignsManager(this);
        ElevatorManager elevatorManager = new ElevatorManager(this);
        metadataManager = new MetadataManager(this);
        checkpointManager = new CheckpointManager();

        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDer Aufzug wurde gestartet");
    }

    @Override
    public void onDisable() {
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
}
