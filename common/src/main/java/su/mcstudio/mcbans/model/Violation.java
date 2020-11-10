package su.mcstudio.mcbans.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:23
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Violation {

    /**
     * Игрок которого наказали
     */
    @NonNull UUID player;
    /**
     * Тот, кто наказал
     */
    @NonNull UUID executor;
    /**
     * Тот, кто отменил наказание
     */
    @Nullable UUID deExecutor;
    /**
     * Причина наказания
     */
    @NonNull String reason;
    /**
     * Вид наказания
     */
    @NonNull ViolationType type;
    /**
     * Время, когда наказали игрока
     */
    long violationTime;
    /**
     * Срок наказания в мс
     */
    long duration;
    /**
     * ID наказания
     */
    @Setter
    @NonFinal
    @NonNull UUID violationId;
    /**
     * Отменено ли наказания
     */
    @Setter
    @NonFinal
    boolean cancelled;

    public Violation(@NonNull UUID violationId, @NonNull UUID playerId,
                     @Nullable UUID executorId, @Nullable UUID deExecutorId,
                     @NonNull String reason, @NonNull ViolationType type,
                     long violationTime, long duration,
                     boolean cancelled) {
        this.violationId = violationId == null ? UUID.randomUUID() : violationId;
        this.player = playerId;
        this.executor = executorId;
        this.deExecutor = deExecutorId;
        this.reason = reason;
        this.type = type;
        this.violationTime = violationTime;
        this.duration = duration;
        this.cancelled = cancelled;
    }
}
