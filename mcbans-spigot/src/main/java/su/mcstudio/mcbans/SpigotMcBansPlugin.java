package su.mcstudio.mcbans;

import dev.simplix.core.common.inject.SimplixInstaller;
import dev.simplix.core.minecraft.spigot.dynamiclisteners.DynamicListenersSimplixModule;
import dev.simplix.core.minecraft.spigot.quickstart.SimplixQuickStart;
import dev.simplix.minecraft.spigot.dynamiccommands.DynamicCommandsSimplixModule;
import org.bukkit.plugin.java.JavaPlugin;

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
        SimplixInstaller.instance()
                        .register(McBansApplication.class,
                                new DynamicListenersSimplixModule(this),
                                new DynamicCommandsSimplixModule());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
