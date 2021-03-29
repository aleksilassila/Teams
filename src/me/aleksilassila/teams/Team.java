package me.aleksilassila.teams;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
    public String name;
    public final ArrayList<UUID> members;
    public UUID leader;

    public boolean changed = false;

    public Team(String name, ArrayList<UUID> members, UUID leader) {
        this.members = members;
        this.leader = leader;
    }

    public void makeLeader(Player player) {
        leader = player.getUniqueId();
        changed = true;
    }

    public void add(Player player) {
        members.add(player.getUniqueId());
        changed = true;
    }

    public void remove(Player player) {
        members.remove(player.getUniqueId());
        changed = true;
    }

    public void saveToDisk() {
        ConfigurationSection section = Config.getConfig().getConfigurationSection(name);

        section.set("members", members);
        section.set("name", name);
        section.set("leader", leader);

        changed = false;
    }
}
