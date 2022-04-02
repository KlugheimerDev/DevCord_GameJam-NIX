package de.nix.dreamvator.misc;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

public class MetadataManager {

    private final Plugin plugin;

    public MetadataManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setMetadata(Metadatable metadatable, String key, Object value) {
        removeMetadata(metadatable, key);
        metadatable.setMetadata(key, (MetadataValue) new FixedMetadataValue(this.plugin, value));
    }

    public void removeMetadata(Metadatable metadatable, String key) {
        if (metadatable.hasMetadata(key))
            metadatable.removeMetadata(key, this.plugin);
    }
}
