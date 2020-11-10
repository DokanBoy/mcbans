package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.ChatEvent;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.ViolationService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 05.11.2020 3:13
 */
@Slf4j
@Component(value = CommonListenerModule.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatListener implements Listener<ChatEvent> {

    ViolationService violationService;
    LocalizationManager localizationManager;

    @Inject
    public ChatListener(ViolationService violationService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.localizationManager = localizationManager;

        Listeners.register(this);
    }

    @Override
    public Class<ChatEvent> type() {
        return ChatEvent.class;
    }

    @Override
    public void handleEvent(@NonNull ChatEvent event) {
        if (violationService.activeMute(event.targetUUID()).isPresent() && !event.message().contains("/"))
            event.canceled(true);
    }
}
