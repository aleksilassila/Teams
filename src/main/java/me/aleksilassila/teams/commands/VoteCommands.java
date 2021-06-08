package me.aleksilassila.teams.commands;

import me.aleksilassila.teams.commands.vote.subcommands.FinishSubcommand;
import me.aleksilassila.teams.commands.vote.subcommands.ResultsSubcommand;
import me.aleksilassila.teams.commands.vote.subcommands.StartSubcommand;
import me.aleksilassila.teams.commands.vote.subcommands.VoteSubcommand;

import java.util.Set;

public class VoteCommands extends Commands {
    @Override
    public void addSubcommands(Set<Subcommand> subcommands) {
        subcommands.add(new FinishSubcommand());
        subcommands.add(new ResultsSubcommand());
        subcommands.add(new StartSubcommand());
    }

    @Override
    public String getName() {
        return "vote";
    }

    @Override
    public Subcommand getDefaultCommand() {
        return new VoteSubcommand();
    }
}
