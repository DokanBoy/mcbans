package su.mcstudio.mcbans.service;

import lombok.NonNull;
import su.mcstudio.mcbans.model.violation.Violation;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:21
 */
public interface ViolationService {

    @NonNull Violation kickPlayer(@NonNull UUID uuid, @NonNull Duration duration);

    @NonNull Violation banPlayer(@NonNull UUID uuid, @NonNull Duration duration, @NonNull String reason);

    @NonNull Violation mutePlayer(@NonNull UUID uuid, @NonNull Duration duration, @NonNull String reason);

}
