package me.aleksilassila.teams.commands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Subcommand {
    public abstract void onCommand(Player player, String[] args, Team team, Player target);

    void run(Player player, String[] args) {
        if (!hasTargetPlayer())
            onCommand(player, args, Config.getPlayerTeam(player), null);
        else if (hasTargetPlayer() && args.length == 0) {
            Messages.send(player, "REQUIRES_TARGET_PLAYER");
        } else {
            Player target = Teams.instance.getServer().getPlayer(args[0]);
            if (target == null)
                Messages.send(player, "PLAYER_NOT_FOUND");
            else
                onCommand(player, args, Config.getPlayerTeam(player), target);
        }

    }

    public abstract boolean hasTargetPlayer();
    public abstract List<String> onTabComplete(Player player, String[] args);
    public abstract String getName();
    public abstract String help();
    public abstract String getPermission();

    protected List<String> getAllPlayers() {
        ArrayList<String> players = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            players.add(p.getName());
        }

        return players;
    }

    protected List<String> getAllPlayers(Team team) {
        if (team == null) return null;

        ArrayList<String> players = new ArrayList<>();

        for (UUID m : team.members) {
            Player p = Bukkit.getPlayer(m);
            if (p == null) continue;
            players.add(p.getName());
        }

        return players;
    }

    protected List<String> getAllTeams() {
        return new ArrayList<>(Config.teams.keySet());
    }
}
