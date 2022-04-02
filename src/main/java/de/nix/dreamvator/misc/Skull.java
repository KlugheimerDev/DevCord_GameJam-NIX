package de.nix.dreamvator.misc;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import jdk.internal.reflect.FieldAccessor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.codehaus.plexus.util.Base64;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum Skull {
    ;

    private static final Base64 base64 = new Base64();
    private String id;

    public static final Map<String, Field> cache = new HashMap<>();

    private Skull(String id) {
        this.id = id;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack getCustomSkull(String url, String name) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        ItemStack head = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        ItemMeta headMeta = head.getItemMeta();
        if (name != null) headMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        Class<?> headMetaClass = headMeta.getClass();
        Reflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
        head.setItemMeta(headMeta);
        return head;
    }

}