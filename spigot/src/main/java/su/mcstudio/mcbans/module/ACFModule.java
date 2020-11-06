package su.mcstudio.mcbans.module;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.Plugin;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 16:49
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ACFModule extends AbstractSimplixModule {

    PaperCommandManager commandManager;

    {
        registerComponentInterceptor(
                BaseCommand.class,
                this::registerCommand);
    }

    public ACFModule(Plugin plugin) {
        this.commandManager = new PaperCommandManager(plugin);
    }

    public void registerCommand(@NonNull final BaseCommand command) {
        commandManager.registerCommand(command);
    }

}
