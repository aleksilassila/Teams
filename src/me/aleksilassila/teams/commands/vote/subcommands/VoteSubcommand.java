package me.aleksilassila.teams.commands.vote.subcommands;

import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoteSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (Teams.currentVote == null) {
            Messages.send(player, "NO_ONGOING_VOTE");
            return;
        }

        if (!Teams.currentVote.assignedTo.contains(player)) {
            Messages.send(player, "VOTE_NOT_ASSIGNED");
            return;
        }

        try {
            int answer = Integer.parseInt(args[0]);
            Teams.currentVote.vote(player, answer);
        } catch (NumberFormatException ignored) { }
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        ArrayList<String> numbers = new ArrayList<>();

        for (int i = 1; i <= Teams.currentVote.options.size(); i++) {
            numbers.add("" + i);
        }

        return numbers;
    }

    @Override
    public String getName() {
        return null;
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
