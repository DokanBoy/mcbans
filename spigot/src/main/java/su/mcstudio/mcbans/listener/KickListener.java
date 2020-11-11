package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import su.mcstudio.mcbans.events.KickEvent;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.util.LocalizationUtil;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 11.11.2020 0:45
 */
@Component(value = CommonListenerModule.class)
public class KickListener implements Listener<KickEvent> {

    private final ViolationService violationService;
    private final LocalizationManager localizationManager;

    @Inject
    public KickListener(ViolationService violationService, @Private LocalizationManager localizationManager) {
        this.violationService = violationService;
        this.localizationManager = localizationManager;

        Listeners.register(this);
    }

    @Override
    public Class<KickEvent> type() {
        return KickEvent.class;
    }

    @Override
    public void handleEvent(@NonNull KickEvent event) {
        Bukkit.getPlayer(event.getPlayerId())
              .kickPlayer(ChatColor.translateAlternateColorCodes('&', LocalizationUtil.getFormattedKickMessage(
                      localizationManager.localized("kick-message"),
                      event.getReason(),
                      event.getExecutorId())
              ));
    }

}