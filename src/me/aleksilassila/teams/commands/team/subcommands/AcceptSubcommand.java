package me.aleksilassila.teams.commands.team.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.List;

public class AcceptSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
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
            Team currentTeam = Config.getPlayerTeam(player);
            if (currentTeam != null) currentTeam.remove(player);
            i.team.add(player);
            Messages.send(player, "JOINED_TEAM", i.team.name);
        } else {
            Messages.send(player, "TEAM_FULL");
        }
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
