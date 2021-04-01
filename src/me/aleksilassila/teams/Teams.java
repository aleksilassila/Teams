package me.aleksilassila.teams;

import me.aleksilassila.teams.commands.Commands;
import me.aleksilassila.teams.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Teams extends JavaPlugin {
    public static Teams instance;
    public static int maxTeamSize = 4;
    public static HashMap<UUID, Invitation> invitations = new HashMap<>();

    @Override
    public void onDisable() {
        Config.save();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        instance = this;
        maxTeamSize = getConfig().getInt("teamSize", 4);

        if (!(new File(getDataFolder() + "/config.yml").exists()))
            saveDefaultConfig();
        Messages.init();
        Config.getConfig();
        new Commands();
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Config::save, 20 * 60 * 5, 20 * 60 * 5);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Config::animateScoreboardTitles, 0, 30);

        for (Player p : Bukkit.getOnlinePlayers()) {
            new PlayerScoreboard(p);
        }

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
            this.time = new Date().getTime();
        }

        public boolean expired() {
            return new Date().getTime() - time >= validForInMs;
        }
    }
}
