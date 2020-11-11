package su.mcstudio.mcbans.module;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

import java.util.Locale;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 16:49
 */
public class ACFModule extends AbstractSimplixModule {

    private final PaperCommandManager commandManager;

    public ACFModule(Plugin plugin) {
        this.commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(new Locale("ru"));

        registerComponentInterceptor(BaseCommand.class, this::registerCommand);
    }

    public void registerCommand(@NonNull final BaseCommand command) {
        commandManager.registerCommand(command, true);
    }

}
