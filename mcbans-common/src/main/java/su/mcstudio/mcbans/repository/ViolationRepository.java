package su.mcstudio.mcbans.repository;

import lombok.NonNull;
import su.mcstudio.mcbans.model.violation.Violation;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:45
 */
public interface ViolationRepository {

    @NonNull Violation createViolation(@NonNull UUID uuid);

    @NonNull Violation findViolation(@NonNull UUID uuid);

    void updateViolation(@NonNull Violation violation);

    void removeViolation(@NonNull Violation violation);


}
