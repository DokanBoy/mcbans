package su.mcstudio.mcbans.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import su.mcstudio.mcbans.module.ACFModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.time.DurationParser;

import java.time.Duration;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 14:20
 */
@Component(value = ACFModule.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BanCommand extends BaseCommand {

    ViolationService violationService;

    @Inject
    public BanCommand(ViolationService violationService) {
        this.violationService = violationService;
    }

    @CommandAlias("ban")
    private void execute(Player player, OfflinePlayer target, String duration, @Optional @Default(value = " ") String reason) {
        java.util.Optional<Duration> optDuration = DurationParser.parseSafely(duration);
        optDuration.ifPresent(value -> violationService.banPlayer(player.getUniqueId(), target.getUniqueId(), reason, value.toMillis()));

    }

}
