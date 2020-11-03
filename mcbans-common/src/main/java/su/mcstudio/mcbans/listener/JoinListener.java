package su.mcstudio.mcbans.listener;

import com.google.inject.Inject;
import dev.simplix.core.common.listener.Listener;
import dev.simplix.core.common.listener.Listeners;
import dev.simplix.core.minecraft.api.events.JoinEvent;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import su.mcstudio.mcbans.model.User;
import su.mcstudio.mcbans.service.UserService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:45
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JoinListener implements Listener<JoinEvent> {

    UserService userService;

    @Inject
    public JoinListener(UserService userService) {
        this.userService = userService;
    }

    public void register() {
        Listeners.register(this);
    }

    @Override
    public Class<JoinEvent> type() {
        return JoinEvent.class;
    }

    @Override
    public void handleEvent(@NonNull JoinEvent joinEvent) {
        User user = userService.findOrCreate(joinEvent.targetUUID());
        System.out.println("HI");
    }

}
