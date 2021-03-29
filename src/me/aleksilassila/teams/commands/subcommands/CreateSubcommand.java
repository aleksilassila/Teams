package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args, Team team, Player target) {
        if (args.length == 0) {
            Messages.send(player, "TEAM_NAME_REQUIRED");
            return;
        }

        String name = args[0];

        if (Config.teams.containsKey(name)) {
            Messages.send(player, "NAME_USED");
            return;
        }

        Config.createTeam(name, player);
    }

    @Override
    public boolean hasTargetPlayer() {
        return false;
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPermission() {
        return Permissions.lead;
    }
}
