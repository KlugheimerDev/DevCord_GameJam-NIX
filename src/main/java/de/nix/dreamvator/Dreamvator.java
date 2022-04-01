package de.nix.dreamvator;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dreamvator extends JavaPlugin {

    public static final String PREFIX = "§9" + ChatColor.of("#3972CB") + "Dreamvator §7» ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDer Aufzug wurde gestartet");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§cNotstopp aktiviert");
    }
}
