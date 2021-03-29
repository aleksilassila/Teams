package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.List;

public class AcceptSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args, Team team, Player target) {
        Teams.Invitation i = Teams.invitations.get(player.getUniqueId());

        if (i == null) {
            Messages.send(player, "NO_INVITATIONS");
            return;
        }

        if (i.expired()) {
            Messages.send(player, "INVITATION_EXPIRED");
            Teams.invitations.remove(player.getUniqueId());
            return;
        }

        if (i.team.members.size() < Teams.maxTeamSize) {
            i.team.add(player);
            if (team != null) team.remove(player);
            Messages.send(player, "JOINED_TEAM", i.team.name);
        } else {
            Messages.send(player, "TEAM_FULL");
        }
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
        return "accept";
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
