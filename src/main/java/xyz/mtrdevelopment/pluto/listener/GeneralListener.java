package xyz.mtrdevelopment.pluto.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.mtrdevelopment.pluto.Pluto;
import xyz.mtrdevelopment.pluto.model.Profile;
import xyz.mtrdevelopment.pluto.utilities.InventoryUtil;

/**
 * GeneralListener is a part of Pluto
 * Which was created / maintained by
 *
 * @author MTR
 */

public class GeneralListener implements Listener {

    private final Pluto instance;

    public GeneralListener(Pluto instance) {
        this.instance = instance;

        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Profile profile = instance.getProfileManager().getProfile(event.getUniqueId());

        if (profile == null) {
            profile = new Profile(event.getUniqueId());
            instance.getProfileManager().save(profile);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event) {
        Profile profile = instance.getProfileManager().getProfile(event.getPlayer().getUniqueId());
        String inventoryTitle = InventoryUtil.getTitle(event.getPlayer().getOpenInventory());

        if (profile == null || inventoryTitle == null) return;

        if (inventoryTitle.startsWith(ChatColor.YELLOW + "Vault #")) {
            int vaultNumber = Integer.parseInt(inventoryTitle.replace(ChatColor.YELLOW + "Vault #", ""));
            if (profile.getVaults().get(vaultNumber) == null) return;

            profile.getVaults().put(vaultNumber, event.getInventory().getContents());
        }
    }
}