package ru.rivendell.rdtravelingbanker.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import ru.rivendell.rdtravelingbanker.RDTravelingBanker;
import ru.rivendell.rdtravelingbanker.config.configurations.main.MainConfig;
import ru.rivendell.rdtravelingbanker.config.configurations.messages.MessagesConfig;

@Singleton
@Getter
public class ConfigRegistrar {

    private MainConfig mainConfig;
    private MessagesConfig messagesConfig;

    @Inject private ConfigLoader configLoader;
    @Inject private RDTravelingBanker plugin;

    public void loadConfigs() {
        plugin.saveResource("messages.json", false);
        plugin.saveResource("config.json", false);

        mainConfig = configLoader.load("config", MainConfig.class);
        messagesConfig = configLoader.load("messages", MessagesConfig.class);
    }

}
