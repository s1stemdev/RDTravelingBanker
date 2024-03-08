package ru.rivendell.rdtravelingbanker;

import com.google.inject.AbstractModule;
import net.milkbowl.vault.economy.Economy;
import ru.rivendell.rdtravelingbanker.command.BankerSpawnCommand;
import ru.rivendell.rdtravelingbanker.command.RandomBankerSpawnCommand;
import ru.rivendell.rdtravelingbanker.config.ConfigLoader;
import ru.rivendell.rdtravelingbanker.config.ConfigRegistrar;
import ru.rivendell.rdtravelingbanker.event.EntityDamageHandler;

public class RDTravelingBankerModule extends AbstractModule {

    private RDTravelingBanker plugin;

    public RDTravelingBankerModule(RDTravelingBanker plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(RDTravelingBanker.class).toInstance(plugin);
        bind(ConfigLoader.class).toInstance(new ConfigLoader());
        bind(ConfigRegistrar.class).toInstance(new ConfigRegistrar());
        bind(BankerSpawnCommand.class).toInstance(new BankerSpawnCommand());
        bind(RandomBankerSpawnCommand.class).toInstance(new RandomBankerSpawnCommand());
        bind(EntityDamageHandler.class).toInstance(new EntityDamageHandler());
    }
}
