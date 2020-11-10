package su.mcstudio.mcbans.model;

import lombok.*;
import org.jetbrains.annotations.Nullable;
import su.mcstudio.mcbans.util.UUIDUtil;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:23
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Violation {

    /**
     * ID наказания
     */
    @Setter
    @NonNull
    private UUID id;

    /**
     * Игрок которого наказали
     */
    @NonNull
    private final UUID player;

    /**
     * Тот, кто наказал
     */
    @NonNull
    private final UUID executor;

    /**
     * Тот, кто отменил наказание
     */
    @Nullable
    private final UUID deExecutor;

    /**
     * Причина наказания
     */
    @NonNull
    private final String reason;

    /**
     * Вид наказания
     */
    @NonNull
    private final ViolationType type;

    /**
     * Время, когда наказали игрока
     */
    private final long violationTime;

    /**
     * Срок наказания в мс
     */
    private final long duration;

    /**
     * Отменено ли наказания
     */
    @Setter
    private boolean cancelled;

    public Violation(@NonNull UUID violationId, @NonNull UUID playerId,
                     @Nullable UUID executorId, @Nullable UUID deExecutorId,
                     @NonNull String reason, @NonNull ViolationType type,
                     long violationTime, long duration,
                     boolean cancelled) {
        this.id = violationId == null ? UUID.randomUUID() : violationId;
        this.player = playerId;
        this.executor = executorId == null ? UUIDUtil.consoleUUID() : executorId;
        this.deExecutor = deExecutorId;
        this.reason = reason;
        this.type = type;
        this.violationTime = violationTime;
        this.duration = duration;
        this.cancelled = cancelled;
    }
}
