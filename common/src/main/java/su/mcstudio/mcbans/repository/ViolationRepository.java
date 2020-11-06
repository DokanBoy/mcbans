package su.mcstudio.mcbans.repository;

import lombok.NonNull;
import su.mcstudio.mcbans.model.Violation;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:45
 */
public interface ViolationRepository {

    @NonNull Violation saveViolation(@NonNull Violation violation);

    @NonNull Violation findViolation(@NonNull UUID violationId);

    @NonNull List<Violation> findByPlayerViolation(@NonNull UUID playerId);

    @NonNull List<Violation> findByExecutorViolation(@NonNull UUID executorId);

    void updateViolation(@NonNull Violation violation);

}
