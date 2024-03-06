package ru.rivendell.rdtravelingbanker.command;

import com.google.inject.Inject;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.command.CommandActor;
import ru.rivendell.rdtravelingbanker.RDTravelingBanker;

public class BankerSpawnCommand {

    @Inject private RDTravelingBanker plugin;

    @Command("banker spawn")
    @CommandPermission("rdtravelingbanker.admin.spawn")
    public void execute(Player player, String id) {
        plugin.getBankerById(id).spawn(player.getLocation());
    }

}
