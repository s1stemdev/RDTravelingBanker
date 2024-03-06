package ru.rivendell.rdtravelingbanker.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageSender {

    public static void sendList(List<Component> messages, Player player) {
        for (Component message : messages) {
            player.sendMessage(message);
        }
    }

}
