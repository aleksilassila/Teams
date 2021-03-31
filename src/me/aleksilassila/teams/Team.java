package me.aleksilassila.teams;

import me.aleksilassila.teams.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {
    public String name;

    public UUID leader;
    List<String> memberLabels = new ArrayList<>();
    private final ArrayList<OfflinePlayer> players = new ArrayList<>();
    public final ArrayList<UUID> members;

    public int points;
    String pointsLabel;

    public Scoreboard scoreboard;
    private final Objective objective;

    public static Scoreboard hiddenScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    public boolean changed = false;

    public Team(String name, ArrayList<UUID> members, UUID leader, int points) {
        this.name = name;
        this.members = members;
        this.leader = leader;
        this.points = points;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(name, "dummy", Messages.get("SIDEBAR_TITLE"));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (UUID member : members) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(member);
            players.add(player);
            memberLabels.add(player.getName());
            if (player.isOnline())
                ((Player) player).setScoreboard(scoreboard);
        }

        updateScoreboard();
    }

    public void makeLeader(OfflinePlayer player) {
        leader = player.getUniqueId();
        changed = true;
    }

    public void add(Player player) {
        members.add(player.getUniqueId());
        changed = true;
        players.add(player);
        player.setScoreboard(scoreboard);
    }

    public void remove(OfflinePlayer player) {
        members.remove(player.getUniqueId());
        changed = true;

        if (members.size() == 0)
            Config.destroyTeam(name);
        players.remove(player);
        if (player.isOnline())
            ((Player) player).setScoreboard(hiddenScoreboard);
    }

    public void updateLeader() {
        if (members.size() != 0) {
            leader = members.get(0);
            changed = true;
        }
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

        changed = false;
    }

    public void updateScoreboard() {
        objective.getScore(Messages.get("SIDEBAR_TEAM_NAME", name))
                .setScore(players.size() + 2);
        objective.getScore("")
                .setScore(players.size() + 2);

        // Update points
        if (pointsLabel != null)
            scoreboard.resetScores(pointsLabel);
        pointsLabel = Messages.get("SIDEBAR_POINTS", points);
        objective.getScore(pointsLabel)
                .setScore(players.size() + 1);

        for (String label : memberLabels)
            scoreboard.resetScores(label);

        memberLabels.clear();
        int i = players.size();
        for (OfflinePlayer p : players) {
            String label = Messages.get(p.isOnline() ? "SIDEBAR_TEAMMATE_ONLINE"
                    : "SIDEBAR_TEAMMATE_OFFLINE", p.getName());
            memberLabels.add(label);
            objective.getScore(label).setScore(i--);
        }
    }
}
