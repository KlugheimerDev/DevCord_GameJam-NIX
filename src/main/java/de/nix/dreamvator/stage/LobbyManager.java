package de.nix.dreamvator.stage;

import de.nix.dreamvator.Dreamvator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyManager implements Listener {

    private final Plugin plugin;
    private boolean lobbyStage = true;

    public LobbyManager(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    //----------------Join und Quit Events
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();

        player.setHealth(20);
        player.setFoodLevel(20);

        if(lobbyStage) {
            event.getPlayer().teleport(new Location(player.getWorld(), -12.5, -59, -37.5, -90, 0));
            event.setJoinMessage(Dreamvator.PREFIX + "§2" + player.getDisplayName() + " §r§7hat das Spiel betreten!");
            player.setGameMode(GameMode.ADVENTURE);
            if (Dreamvator.getPlayers().size() < 2) {
                Dreamvator.getPlayers().add(player);
            } else
                player.sendMessage(Dreamvator.PREFIX + "§7Da schon 2 Spieler zum Spielen eingetragen wurden, konntest du nicht eingetragen werden und wirst beim Start zum Spectator!");
        } else {
            player.sendMessage(Dreamvator.PREFIX + "§7Gerade läuft ein Spiel. Deswegen bist du ein Spectator!");
            player.setGameMode(GameMode.SPECTATOR);
            hidePlayer(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Player player = event.getPlayer();
        if(lobbyStage) {
            event.setQuitMessage(Dreamvator.PREFIX + "§4" + player.getDisplayName() + " §r§7hat das Spiel verlassen!");
            Dreamvator.getPlayers().remove(player);
        }
    }

    //-----------------Lobby Funktion
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
       if(event.getClickedBlock() == null || !lobbyStage) return;
       if(locationsMatch(event.getClickedBlock().getLocation(), -12, -58, -40)) {
           event.setCancelled(true);
           event.getPlayer().sendMessage(Dreamvator.PREFIX + "§7Gerade gibt es nur den Koop-Modus. Adventure Maps machen aber auch mehr Spaß zu zweit, oder?");
       } else if(locationsMatch(event.getClickedBlock().getLocation(), -10, -58, -37)) {
            if(Dreamvator.getPlayers().size() == 2) {
                lobbyStage = false;

                Bukkit.getOnlinePlayers().forEach(player -> {
                    if(Dreamvator.getPlayers().contains(player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 1, false, false, false));
                    } else {
                        player.sendMessage(Dreamvator.PREFIX + "§7Ein Spiel wurde gestartet. Da kein Platz für dich mehr war, bist du nun ein Spectator!");
                        player.setGameMode(GameMode.SPECTATOR);
                        hidePlayer(player);
                    }
                });

                Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendTitle("§5Dreamvator", "§7von dem NiX-Team", 10, 60, 10));

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {

                        Bukkit.broadcastMessage("§7" + Dreamvator.getPlayers().get(0).getDisplayName() + "§7 » §r§fWir haben verschlafen! Wir müssen zur Arbeit!");
                        Bukkit.broadcastMessage("§7" + Dreamvator.getPlayers().get(1).getDisplayName() + "§7 » §r§fLos zum Lift!");

                        Dreamvator.stageManager.setCurrentStage(1);
                    }
                }, 5*20);

            } else {
                Bukkit.broadcastMessage(Dreamvator.PREFIX + "§4Zum Spielstart sind noch zu wenig Spieler da (2 werden benötigt).");
            }
       }
    }

    private void hidePlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(current -> {
            current.hidePlayer(plugin, player);
        });
    }

    private boolean locationsMatch(Location loc1, int x, int y, int z) {
        if(loc1.getBlockX() == x)
            if(loc1.getBlockY() == y)
                if(loc1.getBlockZ() == z)
                    return true;
        return false;
    }
}
