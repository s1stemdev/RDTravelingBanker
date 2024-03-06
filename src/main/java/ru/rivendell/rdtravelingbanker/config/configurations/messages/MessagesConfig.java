package ru.rivendell.rdtravelingbanker.config.configurations.messages;

import lombok.Getter;
import lombok.Setter;
import ru.rivendell.rdtravelingbanker.config.Configurable;

import java.util.List;

@Getter @Setter
public class MessagesConfig extends Configurable {

    private String spawnMessage;
    private String despawnMessage;
    private String actionbarBalance;

    @Override
    protected String getName() {
        return "messages";
    }
}
