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
import su.mcstudio.mcbans.time.DurationParser;
import su.mcstudio.mcbans.util.UUIDUtil;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 14:20
 */
@CommandAlias("mcbans")
@Component(value = ACFModule.class)
public final class MuteCommand extends BaseCommand {

    private final ViolationService violationService;
    private final LocalizationManager localizationManager;

    @Inject
    public MuteCommand(ViolationService violationService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.localizationManager = localizationManager;
    }

    @Subcommand("mute")
    private void execute(CommandIssuer executor, OfflinePlayer target, String duration, @Optional @Default String reason) {
        if (violationService.activeMute(target.getUniqueId()).isPresent()) {
            executor.sendMessage(localizationManager.localized("player-already-muted"));
            return;
        }

        java.util.Optional<Duration> optDuration = DurationParser.parseSafely(duration);
        if (!optDuration.isPresent()) {
            executor.sendMessage(localizationManager.localized("duration-is-not-valid"));
            return;
        }

        final UUID executorId = executor.isPlayer() ? executor.getUniqueId() : UUIDUtil.consoleUUID();
        long parsedDuration = optDuration.get().toMillis();

        violationService.banPlayer(target.getUniqueId(), executorId, reason, parsedDuration);
        localizationManager.localized("successful-muted");
    }

}
