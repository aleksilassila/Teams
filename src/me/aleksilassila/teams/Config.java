package me.aleksilassila.teams;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class Config {
    public static FileConfiguration config;
    private static File configFile;

    public static HashMap<String, Team> teams;

    public static FileConfiguration getConfig() {
        if (config != null) return config;

        configFile = new File(Teams.instance.getDataFolder(), "teams.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Teams.instance.saveResource("teams.yml", false);
         }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        teams = loadTeams();

        return config;
    }

    static HashMap<String, Team> loadTeams() {
        HashMap<String, Team> teams = new HashMap<>();
        for (String teamName : getConfig().getKeys(false)) {
            ArrayList<UUID> members = new ArrayList<>();

            for (String uuid : getConfig().getStringList(teamName + ".members")) {
                try {
                    members.add(UUID.fromString(uuid));
                } catch (IllegalArgumentException ignored) {
                    Teams.instance.getLogger().warning("Team " + teamName + " has an invalid player UUID.");
                }
            }

            UUID leader = parseUUID(getConfig().getString(teamName + ".leader", members.get(0).toString()));

            Team t = new Team(teamName, members, leader);
            teams.put(teamName, t);
        }

        return teams;
    }

    public static Team getPlayerTeam(Player player) {
        for (Team t : teams.values()) {
            if (t.members.contains(player.getUniqueId())) return t;
        }

        return null;
    }

    public static void save() {
        for (Team team : teams.values()) team.saveToDisk();

        saveConfigFile();
    }

    public static void destroyTeam(String name) {
        teams.put(name, null);
        getConfig().set(name, null);
    }

    public static void createTeam(String name, Player player) {
        Team team = new Team(name,
                new ArrayList<>(Collections.singletonList(player.getUniqueId())),
                player.getUniqueId());
        teams.put(name, team);
        team.changed = true;
    }

    private static UUID parseUUID(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    static void saveConfigFile() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            Teams.instance.getLogger().severe("Unable to save teams.yml");
            e.printStackTrace();
        }
    }
}
