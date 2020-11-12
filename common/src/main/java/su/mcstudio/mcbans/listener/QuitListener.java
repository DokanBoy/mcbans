package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.QuitEvent;
import lombok.NonNull;
import su.mcstudio.mcbans.module.CommonListenerModule;
import su.mcstudio.mcbans.service.CacheService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 12.11.2020 5:46
 */
@Component(value = CommonListenerModule.class)
public class QuitListener implements Listener<QuitEvent> {

    private final CacheService cacheService;

    @Inject
    public QuitListener(CacheService cacheService) {
        this.cacheService = cacheService;

        Listeners.register(this);
    }

    @Override
    public Class<QuitEvent> type() {
        return QuitEvent.class;
    }

    @Override
    public void handleEvent(@NonNull QuitEvent event) {
        cacheService.remove(event.target());
    }

}
