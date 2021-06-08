package me.aleksilassila.teams.Event;

import me.aleksilassila.teams.Util.File.Config;
import me.aleksilassila.teams.Util.File.PlayerScoreboard;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        new PlayerScoreboard(e.getPlayer());
        Team team = Config.getPlayerTeam(e.getPlayer());

        if (team != null)
            team.updateScoreboard();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Team team = Config.getPlayerTeam(e.getPlayer());
        if (team != null)
            Bukkit.getScheduler().scheduleSyncDelayedTask(Teams.instance, team::updateScoreboard, 20);
        PlayerScoreboard.scoreboards.remove(e.getPlayer());
    }
}
