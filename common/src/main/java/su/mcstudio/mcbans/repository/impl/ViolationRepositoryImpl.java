package su.mcstudio.mcbans.repository.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.simplix.core.database.sql.function.ResultSetTransformer;
import lombok.NonNull;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.model.ViolationType;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.util.UUIDUtil;
import su.mcstudio.mcbans.util.query.QueryFactory;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:46
 */
@Singleton
public class ViolationRepositoryImpl implements ViolationRepository {

    private final QueryFactory queryFactory;

    private static final String FIND_BY_PLAYER_UUID_QUERY = "SELECT * FROM mcbans_actions WHERE player_uuid=?";
    private static final String FIND_BY_EXECUTOR_UUID_QUERY = "SELECT * FROM mcbans_actions WHERE executor_uuid=?";
    private static final String FIND_BY_VIOLATION_UUID_QUERY = "SELECT * FROM mcbans_actions WHERE violation_uuid=?";
    private static final String INSERT_VIOLATION_QUERY = "INSERT INTO mcbans_actions (violation_uuid, player_uuid, executor_uuid, violation_reason, violation_type, violation_time, violation_duration) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_VIOLATION_QUERY = "UPDATE mcbans_actions SET deexecutor_uuid=?, violation_reason=?, violation_duration=?, violation_cancelled=? WHERE violation_uuid=?";

    ResultSetTransformer<Violation> VIOLATION_TRANSFORMER = rs -> Violation.builder()
                                                                           .id(UUID.fromString(rs.getString("violation_uuid")))
                                                                           .type(ViolationType.valueOf(rs.getString("violation_type").toUpperCase()))
                                                                           .player(UUID.fromString(rs.getString("player_uuid")))
                                                                           .executor(UUID.fromString(rs.getString("executor_uuid")))
                                                                           .deExecutor(rs.getString("deexecutor_uuid") != null ? UUID.fromString(rs.getString("deexecutor_uuid")) : null)
                                                                           .reason(rs.getString("violation_reason"))
                                                                           .violationTime(rs.getLong("violation_time"))
                                                                           .duration(rs.getLong("violation_duration"))
                                                                           .cancelled(rs.getBoolean("violation_cancelled"))
                                                                           .build();

    @Inject
    public ViolationRepositoryImpl(@NonNull QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public @NonNull Violation saveViolation(@NonNull Violation violation) {
        queryFactory.completableQuery()
                    .update(INSERT_VIOLATION_QUERY,
                            violation.getId().toString(),
                            violation.getPlayer().toString(),
                            violation.getExecutor() != null ? violation.getExecutor().toString() : UUIDUtil.consoleUUID().toString(),
                            violation.getReason(),
                            violation.getType().getName(),
                            violation.getViolationTime(),
                            violation.getDuration())
                    .join();
        return violation;
    }

    @Override
    public @NonNull Violation findViolation(@NonNull UUID violationId) {
        return queryFactory.completableQuery()
                           .query(FIND_BY_VIOLATION_UUID_QUERY, VIOLATION_TRANSFORMER::transform, violationId.toString())
                           .join();
    }

    @Override
    public @NonNull List<Violation> findByPlayerViolation(@NonNull UUID playerId) {
        return queryFactory.completableQuery()
                           .query(FIND_BY_PLAYER_UUID_QUERY, rs -> {
                               List<Violation> violations = Lists.newArrayList();
                               while (rs.next()) {
                                   violations.add(VIOLATION_TRANSFORMER.transform(rs));
                               }
                               return violations;
                           }, playerId.toString())
                           .join();
    }

    @Override
    public @NonNull List<Violation> findByExecutorViolation(@NonNull UUID executorId) {
        return queryFactory.completableQuery()
                           .query(FIND_BY_EXECUTOR_UUID_QUERY, rs -> {
                               List<Violation> violations = Lists.newArrayList();
                               while (rs.next()) {
                                   violations.add(VIOLATION_TRANSFORMER.transform(rs));
                               }
                               return violations;
                           }, executorId.toString())
                           .join();
    }

    @Override
    public void updateViolation(@NonNull Violation violation) {
        queryFactory.completableQuery()
                    .update(UPDATE_VIOLATION_QUERY,
                            violation.getDeExecutor() != null ? violation.getDeExecutor().toString() : UUIDUtil.consoleUUID().toString(),
                            violation.getReason(),
                            violation.getDuration(),
                            violation.isCancelled(),
                            violation.getId().toString());
    }

}
