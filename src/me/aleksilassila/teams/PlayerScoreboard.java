package me.aleksilassila.teams;

import me.aleksilassila.teams.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerScoreboard {
    public static HashMap<Player, PlayerScoreboard> scoreboards = new HashMap<>();
    public static Scoreboard hiddenScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    Scoreboard scoreboard;
    Objective objective;

    Player player;
    Team team;

    ArrayList<String> labels = new ArrayList<>();

    public PlayerScoreboard(Player player) {
        scoreboards.put(player, this);
        this.player = player;
        this.team = Config.getPlayerTeam(player);

        // Init and set title
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(player.getName(), "dummy", Messages.get("SIDEBAR_TITLE"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        update();
        player.setScoreboard(scoreboard);
    }

    public void update() {
        for (String label : labels) scoreboard.resetScores(label);
        labels.clear();
        this.team = Config.getPlayerTeam(player);

        labels.add("");
        labels.add(Messages.get("SIDEBAR_EVENT_STARTS_IN", "Waiting..."));
        if (Teams.gamemode != null) {
            String[] combined = new String[]{Messages.get("SIDEBAR_CURRENT_GAMEMODE"), Messages.get("SIDEBAR_CURRENT_GAMEMODE_VALUE", Teams.gamemode)};
            if (String.join("", combined).length() > 40) {
                labels.add(combined[0]);
                labels.add(combined[1]);
            } else
                labels.add(String.join("", combined));
        }
        labels.add("");
        labels.add(Messages.get("SIDEBAR_ONLINE", Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));
        labels.add("");

        // Team stuff
        if (team != null) {
            labels.add(Messages.get("SIDEBAR_TEAM_NAME", team.name));

            for (OfflinePlayer p : team.players) {
                labels.add(Messages.get(p.isOnline() ? "SIDEBAR_TEAMMATE_ONLINE"
                        : "SIDEBAR_TEAMMATE_OFFLINE", p.getName()));
            }

            labels.add("");
            labels.add(Messages.get("SIDEBAR_POINTS", team.points));
            labels.add(Messages.get("SIDEBAR_POINTS_SELF", team.points));
        }

        // Sponsor
        labels.add("");
        labels.add(ChatColor.WHITE + "" + ChatColor.BOLD + "> " + ChatColor.GOLD + "" + ChatColor.BOLD + "MCPR Discord:");
        labels.add(ChatColor.GOLD + "https://discord.gg/gvqcrzCDd8");
        labels.add(ChatColor.WHITE + "" + ChatColor.BOLD + "> " + ChatColor.BLUE + "" + ChatColor.BOLD + "Sponsored by: " + ChatColor.BLUE + "JeekieHost");
        labels.add(ChatColor.DARK_BLUE + "https://discord.gg/rqgYKAZV");

        // Convert line breaks
        for (int i = 0; i < labels.size(); i++) {
            StringBuilder label = new StringBuilder(labels.get(i));

            if (label.toString().equals("")) {
                for (int a = 1; a <= i + 1; a++) {
                    label.append(ChatColor.GOLD);
                }
                labels.set(i, label.toString());
            }

            objective.getScore(label.toString()).setScore(labels.size() - i);
        }
    }

    public static void update(OfflinePlayer player) {
        Player online = player.getPlayer();

        if (online == null) return;

        if (!scoreboards.containsKey(online))
            new PlayerScoreboard(online);
        else
            scoreboards.get(online).update();
    }
}
