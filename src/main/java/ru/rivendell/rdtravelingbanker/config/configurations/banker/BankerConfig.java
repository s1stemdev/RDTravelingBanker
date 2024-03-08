package ru.rivendell.rdtravelingbanker.config.configurations.banker;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.EntityType;
import ru.rivendell.rdtravelingbanker.config.Configurable;

@Getter @Setter
public class BankerConfig extends Configurable {

    private String id;
    private String title;
    private EntityType type;
    private String rarity;
    private int health;
    private int aliveTime;
    private double moneyPerClick;
    private boolean glowing;


    @Override
    protected String getName() {
        return id;
    }
}
