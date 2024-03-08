package ru.rivendell.rdtravelingbanker;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import ru.rivendell.rdtravelingbanker.banker.Banker;
import ru.rivendell.rdtravelingbanker.command.BankerSpawnCommand;
import ru.rivendell.rdtravelingbanker.command.RandomBankerSpawnCommand;
import ru.rivendell.rdtravelingbanker.config.ConfigLoader;
import ru.rivendell.rdtravelingbanker.config.ConfigRegistrar;
import ru.rivendell.rdtravelingbanker.config.configurations.banker.BankerConfig;
import ru.rivendell.rdtravelingbanker.event.EntityDamageHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public final class RDTravelingBanker extends JavaPlugin {

    @Getter private Logger log;

    @Getter private final NamespacedKey key = new NamespacedKey(this, "MONEY");

    @Getter private Economy economy;
    @Getter private List<Banker> bankers;

    private Injector injector;


    @Inject private ConfigLoader configLoader;
    @Inject private ConfigRegistrar configRegistrar;
    @Inject private EntityDamageHandler entityDamageHandler;
    @Inject private BankerSpawnCommand bankerSpawnCommand;
    @Inject private RandomBankerSpawnCommand randomBankerSpawnCommand;

    @Override
    public void onEnable() {
        log = getLogger();

        setupEconomy();

        injector = Guice.createInjector(new RDTravelingBankerModule(this));
        injector.injectMembers(this);

        configRegistrar.loadConfigs();
        bankers = new ArrayList<>();
        loadBankers();

        removeBankers();

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        removeBankers();
    }

    public Banker getRandomBanker() {
        Random random = new Random();

        return bankers.get(random.nextInt(bankers.size()));
    }

    private void removeBankers() {
        for (Entity entity : Bukkit.getWorld(configRegistrar.getMainConfig().getWorldName()).getEntities()) {
            if(entity.getPersistentDataContainer().has(key)) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setHealth(0);
            }
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }


    public Banker getBankerById(String id) {
        for (Banker banker : bankers) {
            if(banker.getConfig().getId().equals(id)) {
                return banker;
            }
        }
        return null;
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(entityDamageHandler, this);
    }

    private void loadBankers() {
        File folder = new File(getDataFolder(), "bankers");

        if(folder.isDirectory()) {
            log.info(Arrays.toString(folder.listFiles()));

            File[] files = folder.listFiles();
            for (File file : files) {
                String name = file.getName().replace(".json", "");

                Banker banker = new Banker(configLoader.load("bankers/" + name, BankerConfig.class), this, configRegistrar);
                bankers.add(banker);

                log.info(name + " loaded successful!");
            }
        }
    }

    private void registerCommands() {
        BukkitCommandHandler handler = BukkitCommandHandler.create(this);

        handler.register(bankerSpawnCommand);
        handler.register(randomBankerSpawnCommand);
    }
}
