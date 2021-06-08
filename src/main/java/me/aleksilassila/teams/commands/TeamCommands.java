package me.aleksilassila.teams.commands;

import me.aleksilassila.teams.commands.team.subcommands.*;

import java.util.Set;

public class TeamCommands extends Commands {
    @Override
    public void addSubcommands(Set<Subcommand> subcommands) {
        subcommands.add(new InviteSubcommand());
        subcommands.add(new KickSubcommand());
        subcommands.add(new RemoveSubcommand());
        subcommands.add(new AddSubcommand());
        subcommands.add(new InfoSubcommand());
        subcommands.add(new CreateSubcommand());
        subcommands.add(new MakeLeaderSubcommand());
        subcommands.add(new AcceptSubcommand());
        subcommands.add(new LeaveSubcommand());
        subcommands.add(new SaySubcommand());
    }

    @Override
    public String getName() {
        return "team";
    }

    @Override
    public Subcommand getDefaultCommand() {
        return null;
    }
}
