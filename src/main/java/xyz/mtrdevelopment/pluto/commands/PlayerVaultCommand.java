package xyz.mtrdevelopment.pluto.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Optional;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.mtrdevelopment.pluto.Pluto;
import xyz.mtrdevelopment.pluto.model.Profile;

/**
 * PlayerVaultCommand is a part of Pluto
 * Which was created / maintained by
 *
 * @author MTR
 */

@RequiredArgsConstructor
public class PlayerVaultCommand extends BaseCommand {

    private final Pluto instance;

    @CommandAlias("playervault|pv|vault")
    public void command(Player player, @Name("number") @Optional Integer number, @Name("target") @Optional OfflinePlayer target) {
        if (!player.hasPermission("pluto.command")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("messages.no-permission", "&cYou do not have permission!")));
        }
        if (number == null || number <= 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("messages.usage-message", "&cUsage: /vault <number>")));
            return;
        }

        if (target == null || !player.hasPermission("pluto.command.others")) {
            boolean hasPermission = player.hasPermission("pluto.vaults." + number);

            if (!hasPermission) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("messages.no-permission-to-use-the-vault", "&cYou do not have permission for this vault!")));
                return;
            }

            Profile profile = instance.getProfileManager().getProfile(player.getUniqueId());

            profile.getVaults().computeIfAbsent(number, k -> new ItemStack[]{});
            Inventory inventory = Bukkit.createInventory(player, instance.getConfig().getInt("settings.vault-size"), ChatColor.YELLOW + "Vault #" + number);

            inventory.setContents(profile.getVaults().get(number));
            player.openInventory(inventory);
        } else {
            Profile profile = instance.getProfileManager().getProfile(target.getUniqueId());
            boolean hasVault = profile.getVaults().get(number) != null;

            if (!hasVault) {
                player.sendMessage(ChatColor.RED + "Error: " + target.getName() + " does not own vault #" + number + "!");
                return;
            }

            profile.getVaults().computeIfAbsent(number, k -> new ItemStack[]{});
            Inventory inventory = Bukkit.createInventory(player, instance.getConfig().getInt("settings.vault-size"), ChatColor.YELLOW + "Vault #" + number);

            inventory.setContents(profile.getVaults().get(number));
            player.openInventory(inventory);
        }
    }
}