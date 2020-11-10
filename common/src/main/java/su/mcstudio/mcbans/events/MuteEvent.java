package su.mcstudio.mcbans.events;

import dev.simplix.core.common.event.AbstractEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 23:18
 */
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MuteEvent extends AbstractEvent {

    @Getter
    UUID playerId;

    @Getter
    UUID executorId;

    @Getter
    long violationTime;

    @Getter
    long duration;

}
