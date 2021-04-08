package me.aleksilassila.teams.commands.team.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class MakeLeaderSubcommand extends Subcommand.LeaderSubcommand {
    @Override
    public void onCommand(Player player, String[] args, Team team, OfflinePlayer target) {
        if (!team.members.contains(target.getUniqueId())) {
            Messages.send(player, "NOT_A_MEMBER");
            return;
        }

        team.makeLeader(target);
        Messages.send(player, "LEADERSHIP_TRANSFERRED");
        team.updateScoreboard();
    }

    @Override
    public boolean canTargetOffline() {
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
