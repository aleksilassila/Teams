package me.aleksilassila.teams.commands.vote.subcommands;

import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

public class FinishSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Teams.currentVote.finish();
        Teams.currentVote.sendResults(player);
    }

    @Override
    public String getName() {
        return "finish";
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPermission() {
        return Permissions.moderate;
    }
}
