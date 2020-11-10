package su.mcstudio.mcbans.service;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import su.mcstudio.mcbans.model.Violation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:21
 */
public interface ViolationService {

    @NonNull Violation kickPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason);

    @NonNull Violation banPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration);

    @NonNull Violation mutePlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration);

    @NonNull List<Violation> unbanPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason);

    @NonNull List<Violation> unmutePlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason);

    @NonNull Optional<Violation> activeMute(@NonNull UUID playerId);

    @NonNull Optional<Violation> activeBan(@NonNull UUID playerId);

}
