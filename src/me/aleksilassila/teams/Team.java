package me.aleksilassila.teams;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
    public String name;
    public final ArrayList<UUID> members;
    public UUID leader;

    public boolean changed = false;

    public Team(String name, ArrayList<UUID> members, UUID leader) {
        this.name = name;
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

        if (members.size() == 0)
            Config.destroyTeam(name);
    }

    public void saveToDisk() {
        if (!changed) return;

        FileConfiguration config = Config.getConfig();
        ArrayList<String> members = new ArrayList<>();

        for (UUID m : this.members)
            members.add(m.toString());

        config.set(name + ".members", members);
        config.set(name + ".leader", leader.toString());

        changed = false;
    }

    public void updateLeader() {
        if (members.size() != 0) {
            leader = members.get(0);
            changed = true;
        }
    }
}
