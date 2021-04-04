package me.aleksilassila.teams.commands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Subcommand {
    public abstract void onCommand(Player player, String[] args);

    public abstract List<String> onTabComplete(Player player, String[] args);
    public abstract String getName();
    public abstract String help();
    public abstract String getPermission();

    public String[] getAliases() {
        return new String[]{};
    }

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

    // TeamSubcommand requires you to be a team leader and a target player as an argument.
    public static abstract class LeaderSubcommand extends Subcommand {
        public abstract void onCommand(Player player, String[] args, Team team, OfflinePlayer target);

        @Override
        public void onCommand(Player player, String[] args) {
            Team team = Config.getPlayerTeam(player);
            if (team == null) {
                Messages.send(player, "MEMBERSHIP_REQUIRED");
                return;
            }
            if (!team.leader.equals(player.getUniqueId())) {
                Messages.send(player, "NOT_LEADER");
                return;
            }

            if (args.length == 0) {
                Messages.send(player, "REQUIRES_TARGET_PLAYER");
            } else {
                OfflinePlayer target = canTargetOffline() ?
                        Bukkit.getServer().getOfflinePlayer(args[0]) :
                        Bukkit.getServer().getPlayer(args[0]);

                if ((canTargetOffline() && !target.hasPlayedBefore()) || (!canTargetOffline() && target == null))
                    Messages.send(player, "PLAYER_NOT_FOUND");
                else if (player.getUniqueId().equals(target.getUniqueId()))
                    Messages.send(player, "CANNOT_PERFORM_ON_SELF");
                else
                    onCommand(player, args, team, target);
            }

        }

        public abstract boolean canTargetOffline();
    }
}
