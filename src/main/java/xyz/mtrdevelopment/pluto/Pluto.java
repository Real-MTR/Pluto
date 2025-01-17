package xyz.mtrdevelopment.pluto;

import co.aikar.commands.BukkitCommandManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.mtrdevelopment.pluto.commands.PlayerVaultCommand;
import xyz.mtrdevelopment.pluto.listener.GeneralListener;
import xyz.mtrdevelopment.pluto.manager.ProfileManager;
import xyz.mtrdevelopment.pluto.serializer.ItemStackSerializer;

@Getter
public final class Pluto extends JavaPlugin {

    private Gson gson;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        registerLibraries();
        loadModels();
        registerListeners();
    }

    private void loadModels() {
        this.profileManager = new ProfileManager(this);
        this.profileManager.load();
    }

    private void registerListeners() {
        new GeneralListener(this);
    }

    private void registerLibraries() {
        this.gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(ItemStack[].class, new ItemStackSerializer())
                .setPrettyPrinting().create();

        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new PlayerVaultCommand(this));
    }

    @Override
    public void onDisable() {
        if (this.profileManager != null) {
            this.profileManager.close();
        }
    }
}