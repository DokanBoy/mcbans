package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.ChatEvent;
import lombok.NonNull;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.util.LocalizationUtil;

import java.util.List;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 05.11.2020 3:13
 */
@Component(value = CommonListenerModule.class)
public class ChatListener implements Listener<ChatEvent> {

    private final ViolationService violationService;
    private final LocalizationManager localizationManager;

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
        List<Violation> activeMutes = violationService.activeMutes(event.targetUUID());

        if (!activeMutes.isEmpty() && !event.message().contains("/")) {
            Violation activeMute = activeMutes.get(0);
            String formattedBanMessage = LocalizationUtil.getFormattedBanMessage(
                    localizationManager.localized("mute-message"),
                    activeMute.getReason(),
                    activeMute.getExecutor(),
                    activeMute.getViolationTime() + activeMute.getDuration());

            event.cancelReason(formattedBanMessage);
            event.canceled(true);
        }
    }

}
