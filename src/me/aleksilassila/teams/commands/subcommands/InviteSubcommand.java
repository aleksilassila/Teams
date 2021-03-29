package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.List;

public class InviteSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args, Team team, Player target) {
        if (team.leader != player.getUniqueId()) {
            Messages.send(player, "NOT_LEADER");
            return;
        }

        if (team.members.contains(target.getUniqueId())) {
            Messages.send(player, "ALREADY_A_MEMBER");
            return;
        }

        if (team.members.size() < Teams.maxTeamSize) {
            Teams.invitations.put(target.getUniqueId(), new Teams.Invitation(target, team));
            Messages.send(player, "PLAYER_INVITED");
            Messages.send(target, "INVITATION_RECEIVED", team.name);
        } else
            Messages.send(player, "TEAM_SIZE_LIMIT");
    }

    @Override
    public boolean hasTargetPlayer() {
        return true;
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return getAllPlayers();
    }

    @Override
    public String getName() {
        return "invite";
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
