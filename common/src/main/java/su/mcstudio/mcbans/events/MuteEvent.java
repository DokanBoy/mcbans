package su.mcstudio.mcbans.events;

import dev.simplix.core.common.event.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 23:18
 */
@AllArgsConstructor
public class MuteEvent extends AbstractEvent {

    @Getter
    private final UUID playerId;

    @Getter
    private final UUID executorId;

    @Getter
    private final String reason;

    @Getter
    private final long violationTime;

    @Getter
    private final long duration;

}
