package su.mcstudio.mcbans.repository.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.simplix.core.database.sql.SqlDatabaseConnection;
import lombok.NonNull;
import su.mcstudio.mcbans.model.violation.Violation;
import su.mcstudio.mcbans.repository.ViolationRepository;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:46
 */
@Singleton
public class ViolationRepositoryImpl implements ViolationRepository {

    @Inject
    public ViolationRepositoryImpl(SqlDatabaseConnection connection) {

    }

    @Override
    public @NonNull Violation createViolation(@NonNull UUID uuid) {
        return null;
    }

    @Override
    public @NonNull Violation findViolation(@NonNull UUID uuid) {
        return null;
    }

    @Override
    public void updateViolation(@NonNull Violation violation) {

    }

    @Override
    public void removeViolation(@NonNull Violation violation) {

    }
}
