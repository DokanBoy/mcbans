package su.mcstudio.mcbans.repository.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.simplix.core.database.sql.SqlDatabaseConnection;
import dev.simplix.core.database.sql.function.ResultSetTransformer;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.model.ViolationType;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.util.UUIDUtil;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:46
 */
@Singleton
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ViolationRepositoryImpl implements ViolationRepository {

    SqlDatabaseConnection connection;

    String FIND_BY_PLAYER_UUID_QUERY = "SELECT * FROM mcbans_actions WHERE player_uuid=?";
    String FIND_BY_EXECUTOR_UUID_QUERY = "SELECT * FROM mcbans_actions WHERE executor_uuid=?";
    String FIND_BY_VIOLATION_UUID_QUERY = "SELECT * FROM mcbans_actions WHERE violation_uuid=?";
    String INSERT_VIOLATION_QUERY = "INSERT INTO mcbans_actions (violation_uuid, player_uuid, executor_uuid, violation_reason, violation_type, violation_time, violation_duration) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_VIOLATION_QUERY = "UPDATE mcbans_actions SET deexecutor_uuid=? violation_reason=? violation_duration=? violation_cancelled=? WHERE violation_uuid=?";

    ResultSetTransformer<Violation> VIOLATION_TRANSFORMER = rs -> Violation.builder()
                                                                           .violationId(UUID.fromString(rs.getString("violation_uuid")))
                                                                           .type(ViolationType
                                                                                   .valueOf(rs.getString("violation_type").toUpperCase()))
                                                                           .player(UUID.fromString(rs.getString("player_uuid")))
                                                                           .executor(UUID.fromString(rs.getString("executor_uuid")))
                                                                           .deExecutor(rs.getString("deexecutor_uuid") != null ? UUID.fromString(rs.getString("deexecutor_uuid")) : null)
                                                                           .reason(rs.getString("violation_reason"))
                                                                           .violationTime(rs.getLong("violation_time"))
                                                                           .duration(rs.getLong("violation_duration"))
                                                                           .cancelled(rs.getBoolean("violation_cancelled"))
                                                                           .build();

    @Inject
    public ViolationRepositoryImpl(@NonNull SqlDatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public @NonNull Violation saveViolation(@NonNull Violation violation) {
        connection.prepare(INSERT_VIOLATION_QUERY, ps -> {
            ps.setString(1, violation.getViolationId().toString());
            ps.setString(2, violation.getPlayer().toString());
            ps.setString(3, violation.getExecutor() != null ? violation.getExecutor().toString() : UUIDUtil.consoleUUID().toString());
            ps.setString(4, violation.getReason());
            ps.setString(5, violation.getType().getName());
            ps.setLong(6, violation.getViolationTime());
            ps.setLong(7, violation.getDuration());
        });
        return violation;
    }

    @Override
    public @NonNull Violation findViolation(@NonNull UUID violationId) {
        return connection.query(FIND_BY_VIOLATION_UUID_QUERY,
                ps -> ps.setString(1, violationId.toString()),
                VIOLATION_TRANSFORMER);
    }

    @Override
    public @NonNull List<Violation> findByPlayerViolation(@NonNull UUID playerId) {
        return connection.query(FIND_BY_PLAYER_UUID_QUERY,
                ps -> ps.setString(1, playerId.toString()),
                rs -> {
                    List<Violation> violations = Lists.newArrayList();
                    while (rs.next()) {
                        violations.add(VIOLATION_TRANSFORMER.transform(rs));
                    }
                    return violations;
                });
    }

    @Override
    public @NonNull List<Violation> findByExecutorViolation(@NonNull UUID executorId) {
        return connection.query(FIND_BY_EXECUTOR_UUID_QUERY,
                ps -> ps.setString(1, executorId.toString()),
                rs -> {
                    List<Violation> violations = Lists.newArrayList();
                    while (rs.next()) {
                        violations.add(VIOLATION_TRANSFORMER.transform(rs));
                    }
                    return violations;
                });
    }

    @Override
    public void updateViolation(@NonNull Violation violation) {
        connection.prepare(UPDATE_VIOLATION_QUERY, ps -> {
            ps.setString(1, violation.getDeExecutor() != null ? violation.getDeExecutor().toString() : UUIDUtil.consoleUUID().toString());
            ps.setString(2, violation.getReason());
            ps.setLong(3, violation.getDuration());
            ps.setBoolean(4, violation.isCancelled());
            ps.setString(5, violation.getViolationId().toString());
        });
    }

}
