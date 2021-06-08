package me.aleksilassila.teams.commands.vote.subcommands;

import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.Util.Messages;
import me.aleksilassila.teams.Util.Permissions.Permissions;
import org.bukkit.entity.Player;

public class ResultsSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (Teams.currentVote == null) {
            Messages.send(player, "NO_ONGOING_VOTE");
            return;
        } else if (Teams.currentVote.finished) {
            Messages.send(player, "PAST_VOTE_RESULT");
        } else {
            Messages.send(player, "ONGOING_VOTE_RESULT");
        }
        Teams.currentVote.sendResults(player);
    }

    @Override
    public String getName() {
        return "results";
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
