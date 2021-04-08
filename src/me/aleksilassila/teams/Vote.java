package me.aleksilassila.teams;

import me.aleksilassila.teams.utils.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Vote {
    public boolean finished = false;

    public String title;
    public ArrayList<String> options;
    public ArrayList<OfflinePlayer> assignedTo;
    public HashMap<OfflinePlayer, Integer> answered = new HashMap<>();

    public Vote(String title, ArrayList<String> options, ArrayList<OfflinePlayer> assignedTo) {
        this.title = title;
        this.options = options;
        this.assignedTo = assignedTo;

        for (OfflinePlayer p : assignedTo) {
            Messages.send(p, "VOTE_STARTED", title);

            for (int i = 1; i <= options.size(); i++) {
                Messages.send(p, "VOTE_OPTION", options.get(i - 1), i);
            }
        }
    }

    public void sendResults(Player player) {
        Messages.send(player, "VOTE_INFO", title, answered.size(), assignedTo.size());
        for (int i = 1; i <= options.size(); i++) {
            String option = options.get(i - 1);

            int ansCount = 0;
            for (Integer a : answered.values()) {
                if (a == i) ansCount++;
            }

            Messages.send(player, "VOTE_INFO_OPTION", option, ansCount);
        }
    }

    public void finish() {
        finished = true;
    }

    public void vote(Player player, int number) {
        if (!assignedTo.contains(player)) {
            Messages.send(player, "VOTE_NOT_ASSIGNED");
            return;
        }

        if (finished) {
            Messages.send(player, "VOTE_ALREADY_FINISHED");
            return;
        }

        Messages.send(player, answered.containsKey(player)
                ? "VOTE_ANSWER_CHANGED" : "VOTE_ANSWERED", number);

        answered.put(player, number);
    }
}
