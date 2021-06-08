package me.aleksilassila.teams.Event;

import me.aleksilassila.teams.Util.File.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.Util.Vote;
import me.aleksilassila.teams.Util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private long startTime;
    public boolean started = false;

    static final int waitTime = 30;

    public void start() {
        startTime = new Date().getTime();
        started = true;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Teams.instance, () -> broadcastTitles(waitTime / 2), 20 * (waitTime / 2));
        Bukkit.getScheduler().scheduleSyncDelayedTask(Teams.instance, () -> broadcastTitles(waitTime - 10), 20 * (waitTime - 10));
        for (int i = 5; i > 0; i--) {
            int finalI = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Teams.instance, () -> broadcastTitles(finalI), 20L * (waitTime - i));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Teams.instance, () -> doVoting(), 20L * waitTime);
    }

    private void broadcastTitles(int i) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle("Event will start in:", i + " second(s)", 2, 16, 2);
        }
    }

    private void doVoting() {
        ArrayList<OfflinePlayer> assigned = new ArrayList<>();

        for (Team team : Config.teams.values()) {
            assigned.addAll(team.players);
        }

        ArrayList<String> options = new ArrayList<>();
        options.add("Gamemode 1");
        options.add("Gamemode 2");
        options.add("Gamemode 3");

        Teams.currentVote = new Vote(Messages.get("VOTE_FIRST_GAMEMODE"), options, assigned);
    }
}
