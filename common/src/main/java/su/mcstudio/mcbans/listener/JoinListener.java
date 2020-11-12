package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.JoinEvent;
import lombok.NonNull;
import lombok.SneakyThrows;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.CacheService;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.util.LocalizationUtil;

import java.util.List;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:45
 */
@Component(value = CommonListenerModule.class)
public class JoinListener implements Listener<JoinEvent> {

    private final ViolationService violationService;
    private final CacheService cacheService;
    private final LocalizationManager localizationManager;

    @Inject
    public JoinListener(ViolationService violationService, CacheService cacheService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.cacheService = cacheService;
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
        List<Violation> activeBans = violationService.activeBans(event.targetUUID());

        if (!activeBans.isEmpty()) {
            Violation activeBan = activeBans.get(0);
            String formattedBanMessage = LocalizationUtil.getFormattedBanMessage(
                    activeBan.getReason(),
                    activeBan.getExecutor(),
                    activeBan.getViolationTime() + activeBan.getDuration());

            event.cancelReason(formattedBanMessage);
            event.canceled(true);
        }

    }

}