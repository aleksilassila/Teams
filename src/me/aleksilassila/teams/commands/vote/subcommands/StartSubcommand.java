package me.aleksilassila.teams.commands.vote.subcommands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.Vote;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Messages;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        Matcher m = Pattern.compile("\"(.*?)\"")
            .matcher(String.join(" ", args));

        while (m.find()) {
            arguments.add(m.group().replace("\"", ""));
        }

        if (arguments.size() <= 1) {
            Messages.send(player, "VOTE_START_ARGUMENTS_REQUIRED");
            return;
        }

        if (Teams.currentVote != null && !Teams.currentVote.finished) {
            Messages.send(player, "ONGOING_VOTE");
            return;
        }

        String title = arguments.get(0);
        arguments.remove(0);

        ArrayList<OfflinePlayer> assigned = new ArrayList<>();

        for (Team team : Config.teams.values()) {
            assigned.addAll(team.players);
        }

        Teams.currentVote = new Vote(title, arguments, assigned);
    }

    @Override
    public String getName() {
        return "start";
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
