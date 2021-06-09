package me.aleksilassila.teams.commands.team.subcommands;

import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class InviteSubcommand extends Subcommand.LeaderSubcommand {
    @Override
    public void onCommand(Player player, String[] args, Team team, OfflinePlayer target) {
        if (team.members.contains(target.getUniqueId())) {
            Messages.send(player, "ALREADY_A_MEMBER");
            return;
        }

        if (team.members.size() < Teams.maxTeamSize) {
            Teams.invitations.put(target.getUniqueId(), new Teams.Invitation((Player) target, team));
            Messages.send(player, "PLAYER_INVITED");
            Messages.send(target, "INVITATION_RECEIVED", player.getName());
        } else
            Messages.send(player, "TEAM_SIZE_LIMIT");
    }

    @Override
    public boolean canTargetOffline() {
        return false;
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
