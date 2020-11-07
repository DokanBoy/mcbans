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
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.OfflinePlayer;
import su.mcstudio.mcbans.module.ACFModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.time.DurationParser;
import su.mcstudio.mcbans.util.LocaleUtil;
import su.mcstudio.mcbans.util.UUIDUtil;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 14:20
 */
@CommandAlias("mcbans")
@Component(value = ACFModule.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BanCommand extends BaseCommand {

    ViolationService violationService;
    LocalizationManager localizationManager;

    @Inject
    public BanCommand(ViolationService violationService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.localizationManager = localizationManager;
    }

    @Subcommand("ban")
    private void execute(CommandIssuer executor, OfflinePlayer target, String duration, @Optional @Default(value = " ") String reason) {
        if (violationService.activeBan(target.getUniqueId()).isPresent()) {
            executor.sendMessage(localizationManager.localized("player-already-banned", LocaleUtil.russianLocale()));
            return;
        }

        java.util.Optional<Duration> optDuration = DurationParser.parseSafely(duration);
        if (!optDuration.isPresent()) {
            executor.sendMessage(localizationManager.localized("duration-is-not-valid", LocaleUtil.russianLocale()));
            return;
        }

        final UUID executorId = executor.isPlayer() ? executor.getUniqueId() : UUIDUtil.consoleUUID();

        optDuration.ifPresent(value -> violationService.banPlayer(target.getUniqueId(), executorId, reason, value.toMillis()));
        localizationManager.localized("successful-banned", LocaleUtil.russianLocale());
    }

}
