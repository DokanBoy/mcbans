package su.mcstudio.mcbans;

import dev.simplix.core.common.inject.SimplixInstaller;
import dev.simplix.core.minecraft.spigot.dynamiclisteners.DynamicListenersSimplixModule;
import dev.simplix.core.minecraft.spigot.quickstart.SimplixQuickStart;
import lombok.SneakyThrows;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import su.mcstudio.mcbans.module.ACFModule;
import su.mcstudio.mcbans.module.CommonLocalizationModule;
import su.mcstudio.mcbans.module.CommonRepositoryModule;
import su.mcstudio.mcbans.module.ConfigurateModule;

import java.io.File;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:12
 */
public final class SpigotMcBansPlugin extends JavaPlugin {

    private final HoconConfigurationLoader configLoader = HoconConfigurationLoader.builder()
                                                                                  .setFile(new File(getDataFolder(), "config.conf"))
                                                                                  .build();
    private ConfigurationNode configuration;

    @Override
    @SneakyThrows
    public void onEnable() {
        if (!SimplixQuickStart.ensureSimplixCore(this)) {
            return;
        }

        //FileUtils.extractResource("/", "config.conf", false);
        configuration = configLoader.load();
        SimplixInstaller.instance()
                        .register(McBansApplication.class,
                                new CommonLocalizationModule(getDataFolder()),
                                new ConfigurateModule(configuration),
                                new CommonRepositoryModule(configuration.getNode("data")),
                                new DynamicListenersSimplixModule(this),
                                new ACFModule(this),
                                binder -> binder.bind(Plugin.class).toInstance(this));
    }

    @Override
    public void onDisable() {

    }

}