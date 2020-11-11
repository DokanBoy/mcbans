package su.mcstudio.mcbans.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import org.bukkit.OfflinePlayer;
import su.mcstudio.mcbans.module.ACFModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.util.UUIDUtil;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 12.11.2020 5:14
 */
@CommandAlias("mcbans")
@Component(value = ACFModule.class)
public final class UnbanCommand extends BaseCommand {

    private final ViolationService violationService;
    private final LocalizationManager localizationManager;

    @Inject
    public UnbanCommand(ViolationService violationService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.localizationManager = localizationManager;
    }

    @Subcommand("unban")
    private void execute(CommandIssuer executor, OfflinePlayer target, @Optional @Default String reason) {
        if (violationService.activeBans(target.getUniqueId()).isEmpty()) {
            executor.sendMessage(localizationManager.localized("player-not-banned"));
            return;
        }

        final UUID executorId = executor.isPlayer() ? executor.getUniqueId() : UUIDUtil.consoleUUID();

        violationService.unbanPlayer(target.getUniqueId(), executorId, reason);
        localizationManager.localized("successful-unbanned");
    }

}
