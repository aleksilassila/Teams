package me.aleksilassila.teams.commands.team.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SaySubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Team team = Config.getPlayerTeam(player);
        if (team == null) {
            Messages.send(player, "MEMBERSHIP_REQUIRED");
            return;
        }

        if (args.length == 0) return;

        for (OfflinePlayer p : team.players) {
            if (p.isOnline()) {
                Messages.send(p, "TEAM_MSG", team.name, player.getDisplayName(), String.join(" ", args));
            }
        }
    }

    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPermission() {
        return Permissions.join;
    }

    @Override
    public String[] getAliases() {
        return new String[] {
                "msg", "pm", "tell"
        };
    }
}
