package ru.rivendell.rdtravelingbanker.command;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import ru.rivendell.rdtravelingbanker.RDTravelingBanker;
import ru.rivendell.rdtravelingbanker.config.ConfigRegistrar;

import java.util.Random;


public class RandomBankerSpawnCommand {

    @Inject private ConfigRegistrar configRegistrar;
    @Inject private RDTravelingBanker plugin;

    @Command("banker randomSpawn")
    @CommandPermission("rdtravelingbanker.admin.randomspawn")
    public void onCommand() {
        Random random = new Random();

        int minX = configRegistrar.getMainConfig().getMinX();
        int maxX = configRegistrar.getMainConfig().getMaxX();
        int minZ = configRegistrar.getMainConfig().getMinZ();
        int maxZ = configRegistrar.getMainConfig().getMaxZ();

        Location location = new Location(
                Bukkit.getServer().getWorld(configRegistrar.getMainConfig().getWorldName()),
                random.nextInt((maxX - minX) + 1) + minX,
                0,
                random.nextInt((maxZ - minZ) + 1) + minZ
        );

        location.setY(location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()) + 1);

        plugin.getRandomBanker().spawn(location);
    }

}
