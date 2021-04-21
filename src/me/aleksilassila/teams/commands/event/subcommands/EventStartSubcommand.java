package me.aleksilassila.teams.commands.event.subcommands;

import me.aleksilassila.teams.Event;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.Subcommand;
import me.aleksilassila.teams.utils.Permissions;
import org.bukkit.entity.Player;

public class EventStartSubcommand extends Subcommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Teams.event = new Event();
        Teams.event.start();
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
