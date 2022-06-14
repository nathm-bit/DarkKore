package io.github.darkkronicle.darkkore.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.config.impl.FileObject;
import io.github.darkkronicle.darkkore.config.impl.JsonFileObject;
import io.github.darkkronicle.darkkore.config.impl.NightFileObject;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.OptionHolder;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class ModConfig implements OptionHolder, ConfigHolder {

    @Getter
    protected FileObject config;

    public abstract File getFile();

    public void setupFileConfig() {
        if (!getFile().exists()) {
            try {
                getFile().getParentFile().mkdirs();
                getFile().createNewFile();
            } catch (IOException e) {
                DarkKore.LOGGER.error("Couldn't initialize config!", e);
            }
        }
        if (getFile().toString().endsWith(".toml")) {
            config = new NightFileObject(FileConfig.of(getFile()));
        } else {
            config = new JsonFileObject(getFile());
        }
    }

    public void save() {
        setupFileConfig();
        config.load();
        for (Option<?> entry : getOptions()) {
            entry.save(config.getConfig());
        }
        config.save();
        config.close();
    }

    public void rawLoad() {
        config.load();
        for (Option<?> entry : getOptions()) {
            entry.load(config.getConfig());
        }
    }

    public void load() {
        setupFileConfig();
        rawLoad();
        config.close();
    }

    public abstract List<Option<?>> getOptions();

    public Screen getScreen() {
        return new ConfigScreen(getOptions());
    }

}
