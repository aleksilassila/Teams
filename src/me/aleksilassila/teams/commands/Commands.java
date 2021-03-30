package me.aleksilassila.teams.commands;

import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.commands.subcommands.*;
import me.aleksilassila.teams.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class Commands implements TabExecutor {
    public final Set<Subcommand> subcommands;

    public Commands() {
        Teams.instance.getCommand("teams").setExecutor(this);

        subcommands = new HashSet<>();

        subcommands.add(new InviteSubcommand());
        subcommands.add(new KickSubcommand());
        subcommands.add(new RemoveSubcommand());
        subcommands.add(new AddSubcommand());
        subcommands.add(new InfoSubcommand());
        subcommands.add(new CreateSubcommand());
        subcommands.add(new MakeLeaderSubcommand());
        subcommands.add(new AcceptSubcommand());
        subcommands.add(new LeaveSubcommand());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            Subcommand target = getSubcommand(args[0]);

            if (target == null) {
                player.sendMessage(Messages.get("error.SUBCOMMAND_NOT_FOUND"));
                getSubcommand("help").onCommand(player, new String[0]);
                return true;
            }

            if (target.getPermission() != null && !player.hasPermission(target.getPermission())) {
                player.sendMessage(Messages.get("error.NO_PERMISSION"));
                return true;
            }

                target.onCommand(player, Arrays.copyOfRange(args, 1, args.length));
            try {

            } catch (Exception e) {
                player.sendMessage(Messages.get("error.ERROR"));
            }

            return true;
        }

        player.sendMessage(Messages.get("VERSION_INFO", Teams.instance.getDescription().getVersion()));

        return true;
    }

    private Subcommand getSubcommand(String name) {
        if (name.split(" ").length > 1) {
            name = name.split(" ")[1];
        }

        for (Subcommand subcommand : subcommands) {
            if (subcommand.getName().equalsIgnoreCase(name)) return subcommand;
        }

        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return null;

        Player player = (Player) sender;

        List<String> availableArgs = new ArrayList<>();

        if (args.length == 1) {
            for (Subcommand subcommand : subcommands) {
                if (subcommand.getPermission() == null || player.hasPermission(subcommand.getPermission()))
                    availableArgs.add(subcommand.getName());
            }
        } else if (args.length > 1) {
            Subcommand currentSubcommand = getSubcommand(args[0]);
            if (currentSubcommand == null) return null;

            if (currentSubcommand.getPermission() == null || player.hasPermission(currentSubcommand.getPermission()))
                availableArgs = currentSubcommand.onTabComplete(player, Arrays.copyOfRange(args, 1, args.length));
        }

        return availableArgs;
    }
}
