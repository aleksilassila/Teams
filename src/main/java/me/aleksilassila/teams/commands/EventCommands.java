package me.aleksilassila.teams.commands;

import me.aleksilassila.teams.commands.event.subcommands.EventStartSubcommand;
import me.aleksilassila.teams.commands.event.subcommands.SetGamemodeSubcommand;

import java.util.Set;

public class EventCommands extends Commands {
    @Override
    public void addSubcommands(Set<Subcommand> subcommands) {
        subcommands.add(new EventStartSubcommand());
        subcommands.add(new SetGamemodeSubcommand());
    }

    @Override
    public String getName() {
        return "event";
    }

    @Override
    public Subcommand getDefaultCommand() {
        return null;
    }
}
