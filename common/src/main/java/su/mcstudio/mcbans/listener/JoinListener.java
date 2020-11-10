package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.JoinEvent;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.util.LocalizationUtil;

import java.time.Duration;
import java.util.Optional;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:45
 */
@Slf4j
@Component(value = CommonListenerModule.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JoinListener implements Listener<JoinEvent> {

    ViolationService violationService;
    LocalizationManager localizationManager;

    @Inject
    public JoinListener(ViolationService violationService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.localizationManager = localizationManager;

        Listeners.register(this);
    }

    @Override
    public Class<JoinEvent> type() {
        return JoinEvent.class;
    }

    @Override
    @SneakyThrows
    public void handleEvent(@NonNull JoinEvent event) {
        Violation muteVl = violationService.mutePlayer(event.targetUUID(), event.targetUUID(), "§cTEST", Duration.ofMinutes(5).toMillis());

        Optional<Violation> activeBan = violationService.activeBan(event.targetUUID());
        if (activeBan.isPresent()) {
            String formattedBanMessage = LocalizationUtil.getFormattedBanMessage(
                    localizationManager.localized("ban-message"),
                    activeBan.get().getReason(),
                    activeBan.get().getExecutor(),
                    activeBan.get().getViolationTime() + activeBan.get().getDuration());
            event.cancelReason(formattedBanMessage);
            event.canceled(true);
        }

    }

}