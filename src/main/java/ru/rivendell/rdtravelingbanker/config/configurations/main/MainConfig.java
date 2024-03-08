package ru.rivendell.rdtravelingbanker.config.configurations.main;

import lombok.Getter;
import lombok.Setter;
import ru.rivendell.rdtravelingbanker.config.Configurable;

@Getter @Setter
public class MainConfig extends Configurable {

    private String worldName;
    private int maxX;
    private int maxZ;
    private int minX;
    private int minZ;

    @Override
    protected String getName() {
        return "config";
    }
}
