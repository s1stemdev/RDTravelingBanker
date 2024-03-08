package ru.rivendell.rdtravelingbanker.banker;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import ru.rivendell.rdtravelingbanker.RDTravelingBanker;
import ru.rivendell.rdtravelingbanker.config.ConfigRegistrar;
import ru.rivendell.rdtravelingbanker.config.configurations.banker.BankerConfig;
import ru.rivendell.rdtravelingbanker.utils.TextSerializer;

public class Banker {

    private RDTravelingBanker plugin;
    private ConfigRegistrar configRegistrar;

    @Getter private BankerConfig config;

    public Banker(BankerConfig config, RDTravelingBanker plugin, ConfigRegistrar configRegistrar) {
        this.config = config;
        this.plugin = plugin;
        this.configRegistrar = configRegistrar;
    }

    public void spawn(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, config.getType());

        entity.setCustomNameVisible(true);
        entity.setCustomName(TextSerializer.serializeSection(config.getTitle()));
        entity.setGlowing(config.isGlowing());

        PersistentDataContainer container = entity.getPersistentDataContainer();
        container.set(plugin.getKey(), PersistentDataType.DOUBLE, config.getMoneyPerClick());

        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setMaxHealth(config.getHealth());
        livingEntity.setHealth(config.getHealth());

        Component spawnMessage = TextSerializer.serializeComponent(configRegistrar.getMessagesConfig().getSpawnMessage()
                .replace("{rarity}", config.getRarity())
                .replace("{minutes}", String.valueOf(config.getAliveTime()))
                .replace("{x}", String.valueOf(location.getBlockX()))
                .replace("{y}", String.valueOf(location.getBlockY()))
                .replace("{z}", String.valueOf(location.getBlockZ()))
        );

        Component despawnMessage = TextSerializer.serializeComponent(configRegistrar.getMessagesConfig().getDespawnMessage());

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.sendMessage(spawnMessage);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(config.getAliveTime() * 60000L);

                   new BukkitRunnable() {
                        @Override
                        public void run() {
                            livingEntity.setHealth(0);
                        }
                    }.runTask(plugin);

                    for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                        onlinePlayer.sendMessage(despawnMessage);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }}.runTaskAsynchronously(plugin);
    }

}
