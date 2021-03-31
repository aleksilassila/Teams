package me.aleksilassila.teams;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Team team = Config.getPlayerTeam(e.getPlayer());
        if (team != null) {
            team.updateScoreboard();
            e.getPlayer().setScoreboard(team.scoreboard);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Team team = Config.getPlayerTeam(e.getPlayer());
        if (team != null)
            Bukkit.getScheduler().scheduleSyncDelayedTask(Teams.instance, team::updateScoreboard, 20);
    }
}
