package xyz.mtrdevelopment.pluto.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Profile is a part of Pluto
 * Which was created / maintained by
 *
 * @author MTR
 */

@Data
public class Profile {

    @SerializedName("_id")
    private final UUID uuid;
    private final Map<Integer, ItemStack[]> vaults = new HashMap<>();
}