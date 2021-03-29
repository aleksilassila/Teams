package me.aleksilassila.teams;

import me.aleksilassila.teams.commands.Commands;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Teams extends JavaPlugin {
    public static Teams instance;
    public static int maxTeamSize = 4;
    public static HashMap<UUID, Invitation> invitations;

    @Override
    public void onDisable() {
        Config.save();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        maxTeamSize = getConfig().getInt("teamSize", 4);
        instance = this;

        Config.getConfig();

        new Commands();

        super.onEnable();
    }

    public static class Invitation {
        public long time;
        private final long validForInMs = 15 * 1000;
        public final Player receiver;
        public final Team team;

        public Invitation(Player receiver, Team team) {
            this.receiver = receiver;
            this.team = team;
        }

        public boolean expired() {
            return new Date().getTime() - time >= validForInMs;
        }
    }
}
