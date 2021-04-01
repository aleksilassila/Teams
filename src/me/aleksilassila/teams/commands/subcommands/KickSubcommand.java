package me.aleksilassila.teams.commands.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.PlayerScoreboard;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class KickSubcommand extends Subcommand.LeaderSubcommand {
    @Override
    public void onCommand(Player player, String[] args, Team team, OfflinePlayer target) {
        if (!team.members.contains(target.getUniqueId())) {
            Messages.send(player, "NOT_A_MEMBER");
            return;
        }

        team.remove(target);
        Messages.send(player, "MEMBER_KICKED");
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
        return "kick";
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
