package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveSubcommand extends Subcommand {

    @Override
    public void onCommand(Player player, String[] args, Team team, Player target) {
        if (args.length != 2) {
            Messages.send(player, "INVALID_ARGUMENTS");
            return;
        }

        Team targetTeam = Config.teams.get(args[1]);

        if (targetTeam == null) {
            Messages.send(player, "TEAM_NOT_FOUND");
            return;
        }

        targetTeam.add(target);
    }

    @Override
    public boolean hasTargetPlayer() {
        return true;
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
        return "remove";
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
