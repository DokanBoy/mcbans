package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
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

import java.time.Duration;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:45
 */
@Slf4j
@Component(value = CommonListenerModule.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JoinListener implements Listener<JoinEvent> {

    ViolationService violationService;

    @Inject
    public JoinListener(ViolationService violationService) {
        this.violationService = violationService;

        Listeners.register(this);
    }

    @Override
    public Class<JoinEvent> type() {
        return JoinEvent.class;
    }

    @Override
    @SneakyThrows
    public void handleEvent(@NonNull JoinEvent event) {
        Violation violation = violationService.mutePlayer(event.targetUUID(), null, "§aTEST", Duration.ofMinutes(5).toMillis());
        log.info("User ID: " + event.targetUUID());
        log.info("User IP: " + event.targetAddress());
        log.info(violation.toString());

        if (violationService.isBanned(event.targetUUID()) != null)
            event.canceled(true);
    }

}