package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import lombok.NonNull;
import org.bukkit.Bukkit;
import su.mcstudio.mcbans.events.BanEvent;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.util.LocalizationUtil;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 11.11.2020 0:03
 */
@Component(value = CommonListenerModule.class)
public class BanListener implements Listener<BanEvent> {

    private final ViolationService violationService;
    private final LocalizationManager localizationManager;

    @Inject
    public BanListener(ViolationService violationService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.localizationManager = localizationManager;

        Listeners.register(this);
    }

    @Override
    public Class<BanEvent> type() {
        return BanEvent.class;
    }

    @Override
    public void handleEvent(@NonNull BanEvent event) {
        Bukkit.getPlayer(event.getPlayerId())
              .kickPlayer(LocalizationUtil.getFormattedBanMessage(
                      event.getReason(),
                      event.getExecutorId(),
                      event.getDuration())
              );
    }

}
