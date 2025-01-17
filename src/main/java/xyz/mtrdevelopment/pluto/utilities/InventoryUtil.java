package xyz.mtrdevelopment.pluto.utilities;

import java.lang.reflect.Method;

public class InventoryUtil {

    private static final Method TITLE;

    static {
        try {
            Class<?> clazz = Class.forName("org.bukkit.inventory.InventoryView");

            TITLE = clazz.getDeclaredMethod("getTitle");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getTitle(Object view) {
        try {
            return (String) TITLE.invoke(view);
        } catch (Exception e) {
            return null;
        }
    }
}
