package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.List;

public class MakeLeaderSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args, Team team, Player target) {
        if (!team.members.contains(player.getUniqueId()) || team.leader != player.getUniqueId()) {
            Messages.send(player, "NOT_LEADER");
            return;
        }

        if (!team.members.contains(target.getUniqueId())) {
            Messages.send(player, "NOT_A_MEMBER");
            return;
        }

        team.makeLeader(target);
        Messages.send(player, "LEADERSHIP_TRANSFERRED");
    }

    @Override
    public boolean hasTargetPlayer() {
        return true;
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return getAllPlayers(Config.getPlayerTeam(player));
    }

    @Override
    public String getName() {
        return "makeleader";
    }

    @Override
    public String help() {
        return "Transfer team leadership.";
    }

    @Override
    public String getPermission() {
        return Permissions.lead;
    }
}
