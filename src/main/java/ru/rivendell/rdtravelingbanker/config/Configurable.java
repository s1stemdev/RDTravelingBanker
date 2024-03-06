package ru.rivendell.rdtravelingbanker.config;

import org.bukkit.plugin.java.JavaPlugin;
import ru.rivendell.rdtravelingbanker.RDTravelingBanker;

public abstract class Configurable {

    public RDTravelingBanker getPlugin() {
        return JavaPlugin.getPlugin(RDTravelingBanker.class);
    }

    protected abstract String getName();

}
