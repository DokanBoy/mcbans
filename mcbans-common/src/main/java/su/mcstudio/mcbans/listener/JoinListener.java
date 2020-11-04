package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.JoinEvent;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import su.mcstudio.mcbans.model.User;
import su.mcstudio.mcbans.module.CommonServiceModule;
import su.mcstudio.mcbans.service.UserService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:45
 */
@Slf4j
@Component(value = CommonServiceModule.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JoinListener implements Listener<JoinEvent> {

    UserService userService;

    @Inject
    public JoinListener(UserService userService) {
        this.userService = userService;

        Listeners.register(this);
    }

    @Override
    public Class<JoinEvent> type() {
        return JoinEvent.class;
    }

    @Override
    public void handleEvent(@NonNull JoinEvent joinEvent) {
        User user = userService.findOrCreate(joinEvent.targetUUID());
        log.info("User ID: " + user.getUuid().toString());
    }

}