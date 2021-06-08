package me.aleksilassila.teams.commands.team.subcommands;

import me.aleksilassila.teams.Util.File.Config;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.Util.Messages;
import me.aleksilassila.teams.Util.Permissions.Permissions;
import org.bukkit.entity.Player;

public class CreateSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (Config.getPlayerTeam(player) != null) {
            Messages.send(player, "YOURE_IN_A_TEAM");
            return;
        }

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
        Messages.send(player, "TEAM_CREATED");
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
