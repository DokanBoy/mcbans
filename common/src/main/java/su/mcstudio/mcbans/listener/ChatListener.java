package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.ChatEvent;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.ViolationService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 05.11.2020 3:13
 */
@Component(value = CommonListenerModule.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatListener implements Listener<ChatEvent> {

    ViolationService violationService;

    @Inject
    public ChatListener(ViolationService violationService) {
        this.violationService = violationService;

        Listeners.register(this);
    }

    @Override
    public Class<ChatEvent> type() {
        return ChatEvent.class;
    }

    @Override
    public void handleEvent(@NonNull ChatEvent event) {
        if (violationService.isMuted(event.targetUUID()) != null)
            event.canceled(true);
    }
}
