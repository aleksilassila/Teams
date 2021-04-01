package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.PlayerScoreboard;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Team team = Config.getPlayerTeam(player);
        if (team == null) {
            Messages.send(player, "MEMBERSHIP_REQUIRED");
            return;
        }

        team.remove(player);

        if (team.leader.equals(player.getUniqueId()))
            team.updateLeader();

        Messages.send(player, "TEAM_LEFT");
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPermission() {
        return Permissions.join;
    }
}
