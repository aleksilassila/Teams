package me.aleksilassila.teams.commands;

import me.aleksilassila.teams.Config;
import me.aleksilassila.teams.Team;
import me.aleksilassila.teams.Teams;
import me.aleksilassila.teams.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class Commands implements TabExecutor {
    public final Set<Subcommand> subcommands;

    public Commands() {
        Teams.instance.getCommand(getName()).setExecutor(this);

        subcommands = new HashSet<>();

        addSubcommands(subcommands);
    }

    public abstract void addSubcommands(Set<Subcommand> subcommands);
    public abstract String getName();
    public abstract Subcommand getDefaultCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            Subcommand target = getSubcommand(args[0]);

            if (target == null) {
                if (getDefaultCommand() == null)
                    Messages.send(player, "SUBCOMMAND_NOT_FOUND");
                else
                    getDefaultCommand().onCommand(player, args);
                return true;
            }

            if (target.getPermission() != null && !player.hasPermission(target.getPermission())) {
                player.sendMessage(Messages.get("NO_PERMISSION"));
                return true;
            }

                target.onCommand(player, Arrays.copyOfRange(args, 1, args.length));
            try {
            } catch (Exception e) {
                player.sendMessage(Messages.get("ERROR"));
            }

            return true;
        }

        player.sendMessage(Messages.get("VERSION_INFO", Teams.instance.getDescription().getVersion()));
        Team team = Config.getPlayerTeam(player);

        if (team != null) {
            Messages.send(player, "MY_TEAM", team.name);

            for (UUID member : team.members) {
                String name = Bukkit.getOfflinePlayer(member).getName();

                if (name != null) {
                    if (team.leader == member)
                        Messages.send(player, "TEAM_LEADER", name);
                    else Messages.send(player, "TEAM_MEMBER", name);
                }
            }
        }

        return true;
    }

    private Subcommand getSubcommand(String name) {
        if (name.split(" ").length > 1) {
            name = name.split(" ")[1];
        }

        for (Subcommand subcommand : subcommands) {
            if (name.equalsIgnoreCase(subcommand.getName())) return subcommand;

            for (String alias : subcommand.getAliases()) {
                if (alias.equalsIgnoreCase(name)) return subcommand;
            }
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
                if (subcommand.getPermission() == null || player.hasPermission(subcommand.getPermission())) {
                    if (subcommand.getName() != null)
                        availableArgs.add(subcommand.getName());
                }
            }
            Subcommand def = getDefaultCommand();
            if (def != null && player.hasPermission(def.getPermission()))
                availableArgs.addAll(def.onTabComplete(player, args));

        } else if (args.length > 1) {
            Subcommand currentSubcommand = getSubcommand(args[0]);
            if (currentSubcommand == null) return null;

            if (currentSubcommand.getPermission() == null || player.hasPermission(currentSubcommand.getPermission()))
                availableArgs = currentSubcommand.onTabComplete(player, Arrays.copyOfRange(args, 1, args.length));
        }

        return availableArgs;
    }
}
