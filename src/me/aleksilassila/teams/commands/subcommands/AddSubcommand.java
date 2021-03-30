package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class AddSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length != 2) {
            Messages.send(player, "INVALID_ARGUMENTS");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            Messages.send(player, "PLAYER_NOT_FOUND");
            return;
        }

        Team targetTeam = Config.teams.get(args[1]);

        if (targetTeam == null) {
            Messages.send(player, "TEAM_NOT_FOUND");
            return;
        }

        targetTeam.add(targetPlayer);
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        if (args.length == 1)
            return getAllPlayers();
        if (args.length == 2)
            return getAllTeams();
        return null;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPermission() {
        return Permissions.moderate;
    }
}
