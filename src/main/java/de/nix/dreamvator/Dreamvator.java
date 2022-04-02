package de.nix.dreamvator;

import de.nix.dreamvator.elevator.ElevatorManager;
import de.nix.dreamvator.misc.MetadataManager;
import de.nix.dreamvator.signs.SignsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dreamvator extends JavaPlugin {

    private Dreamvator instance;

    MetadataManager metadataManager;

    public static final String PREFIX = "§9" + ChatColor.of("#3972CB") + "Dreamvator §7» ";

    @Override
    public void onEnable() {
        this.instance = this;

        SignsManager signsManager = new SignsManager(this);
        ElevatorManager elevatorManager = new ElevatorManager(this);
        metadataManager = new MetadataManager(this);

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
}
