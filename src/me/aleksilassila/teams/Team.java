package me.aleksilassila.teams;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {
    public String name;

    public UUID leader;
    List<String> memberLabels = new ArrayList<>();
    public final ArrayList<OfflinePlayer> players = new ArrayList<>();
    public final ArrayList<UUID> members;
    public int points;

    public boolean changed = false;

    public Team(String name, ArrayList<UUID> members, UUID leader, int points) {
        this.name = name;
        this.members = members;
        this.leader = leader;
        this.points = points;

        for (UUID member : members) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(member);
            players.add(player);
            memberLabels.add(player.getName());
        }
    }

    public void makeLeader(OfflinePlayer player) {
        leader = player.getUniqueId();
        changed = true;
        Teams.instance.getLogger().info("Team " + name
                + " leader is now " + player.getName());
    }

    public void add(Player player) {
        members.add(player.getUniqueId());
        changed = true;
        players.add(player);
        updateScoreboard();
        Teams.instance.getLogger().info("Player " + player.getName()
                + " added to team " + name);
    }

    public void remove(OfflinePlayer player) {
        members.remove(player.getUniqueId());
        changed = true;

        if (members.size() == 0)
            Config.destroyTeam(name);
        players.remove(player);
        updateScoreboard();
        PlayerScoreboard.update(player);
        Teams.instance.getLogger().info("Player " + player.getName()
                + " removed from team " + name);
    }

    public void updateLeader() {
        if (members.size() != 0) {
            leader = members.get(0);
            changed = true;
        }

        Teams.instance.getLogger().info("Team " + name
                + " leader is now " + leader.toString());
    }

    public void setPoints(int points) {
        this.points = points;
        updateScoreboard();
    }

    public void saveToDisk() {
        if (!changed) return;

        FileConfiguration config = Config.getConfig();
        ArrayList<String> members = new ArrayList<>();

        for (UUID m : this.members)
            members.add(m.toString());

        config.set(name + ".members", members);
        config.set(name + ".leader", leader.toString());
        config.set(name + ".points", points);

        changed = false;
    }

    public void updateScoreboard() {
        for (OfflinePlayer player : players) {
            PlayerScoreboard.update(player);
        }
    }
}
