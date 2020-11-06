package su.mcstudio.mcbans;

import de.leonhard.storage.LightningBuilder;
import dev.simplix.core.common.inject.SimplixInstaller;
import dev.simplix.core.minecraft.spigot.dynamiclisteners.DynamicListenersSimplixModule;
import dev.simplix.core.minecraft.spigot.quickstart.SimplixQuickStart;
import dev.simplix.minecraft.spigot.dynamiccommands.DynamicCommandsSimplixModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import su.mcstudio.mcbans.module.*;

import java.io.File;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:12
 */
public final class SpigotMcBansPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!SimplixQuickStart.ensureSimplixCore(this)) {
            return;
        }
        saveDefaultConfig();
        SimplixInstaller.instance()
                        .register(McBansApplication.class,
                                new CommonListenerModule(),
                                new LocalizationModule(getDataFolder()),
                                new CommonServiceModule(),
                                new CommonRepositoryModule(LightningBuilder.fromFile(new File(getDataFolder(), "config.yml")).createYaml()),
                                new DynamicListenersSimplixModule(this),
                                new ACFModule(this),
                                new DynamicCommandsSimplixModule());
    }

    @Override
    public void onDisable() {
    }

}