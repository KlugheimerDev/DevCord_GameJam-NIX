package de.nix.dreamvator;

import de.nix.dreamvator.signs.SignsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dreamvator extends JavaPlugin {

    private Dreamvator instance;

    public static final String PREFIX = "§9" + ChatColor.of("#3972CB") + "Dreamvator §7» ";

    @Override
    public void onEnable() {
        this.instance = this;

        SignsManager signsManager = new SignsManager(this);

        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDer Aufzug wurde gestartet");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§cNotstopp aktiviert");
    }

    public Dreamvator getInstance() {
        return instance;
    }
}
