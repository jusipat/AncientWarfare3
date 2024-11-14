package xyz.dylanlogan.ancientwarfare.core.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * static-data configuration class.
 * Each mod will need to construct its own subclass of this, adding static fields for necessary config items
 *
 * 
 */
public abstract class ModConfiguration {
    /**
     * category names
     */
    public static final String generalOptions = "01_shared_settings";
    public static final String serverOptions = "02_server_settings";
    public static final String clientOptions = "03_client_settings";
    public static final String configPathForFiles = "config/ancientwarfare/";
    protected final Configuration config;
    public boolean updatedVersion = false;
    public boolean autoExportOnUpdate = false;

    public ModConfiguration(Configuration config) {
        this.config = config;
        load();
    }

    public ModConfiguration(String modid){
        this(getConfigFor(modid));
    }

    public void load() {
        initializeCategories();
        initializeValues();
        save();
    }

    protected abstract void initializeCategories();

    protected abstract void initializeValues();

    public Configuration getConfig() {
        return this.config;
    }

    public void save() {
        if(config.hasChanged())
            config.save();
    }

    public boolean updatedVersion() {
        return updatedVersion;
    }

    public boolean autoExportOnUpdate() {
        return autoExportOnUpdate;
    }

    public static Configuration getConfigFor(String modID) {
        return new Configuration(new File(configPathForFiles, modID + ".cfg"));
    }
}
