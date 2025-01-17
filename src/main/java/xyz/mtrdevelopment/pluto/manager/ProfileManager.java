package xyz.mtrdevelopment.pluto.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import xyz.mtrdevelopment.pluto.Pluto;
import xyz.mtrdevelopment.pluto.model.Profile;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProfileManager is a part of Pluto
 * Which was created / maintained by
 *
 * @author MTR
 */

@Getter
@RequiredArgsConstructor
public class ProfileManager {

    private final Pluto instance;
    private final Map<UUID, Profile> profileMap = new ConcurrentHashMap<>();

    public void load() {
        ConfigurationSection section = instance.getConfig().getConfigurationSection("data");

        if (section != null) {
            for (String key : section.getKeys(false)) {
                // Made it putIfAbsent in-case there are duplications of profiles
                profileMap.putIfAbsent(UUID.fromString(key), instance.getGson().fromJson(section.getString(key), Profile.class));
            }
        }
    }

    public Profile getProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    public void save(Profile profile) {
        profileMap.putIfAbsent(profile.getUuid(), profile);
    }

    public void delete(Profile profile) {
        CompletableFuture.runAsync(() -> {
            profileMap.remove(profile.getUuid());

            instance.getConfig().set("data." + profile.getUuid().toString(), null);
            instance.saveConfig();
        });
    }

    public void close() {
        profileMap.values().forEach(profile -> {
            instance.getConfig().set("data." + profile.getUuid().toString(), instance.getGson().toJson(profile));
            instance.saveConfig();
        });
    }
}