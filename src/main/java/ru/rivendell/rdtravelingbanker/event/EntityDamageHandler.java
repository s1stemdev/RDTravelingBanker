package ru.rivendell.rdtravelingbanker.event;

import com.google.inject.Inject;
import net.md_5.bungee.api.ChatMessageType;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import ru.rivendell.rdtravelingbanker.RDTravelingBanker;
import ru.rivendell.rdtravelingbanker.config.ConfigRegistrar;
import ru.rivendell.rdtravelingbanker.utils.TextSerializer;

public class EntityDamageHandler implements Listener {

    @Inject private RDTravelingBanker plugin;
    @Inject private ConfigRegistrar configRegistrar;


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getDamageSource().getCausingEntity() instanceof Player)) return;
        if(!(event.getEntity().getPersistentDataContainer().has(plugin.getKey()))) return;

        Player player = (Player) event.getDamageSource().getCausingEntity();
        double amount = event.getEntity().getPersistentDataContainer().get(plugin.getKey(), PersistentDataType.DOUBLE);

        plugin.getEconomy().depositPlayer(player, amount);

        player.sendActionBar(
                TextSerializer.serializeComponent(
                configRegistrar.getMessagesConfig().getActionbarBalance().replace("{amount}", String.valueOf(amount)))
        );
    }

}
