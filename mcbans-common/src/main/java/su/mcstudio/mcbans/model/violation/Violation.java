package su.mcstudio.mcbans.model.violation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:23
 */
@Data
@Builder
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
    @NonNull UUID deExecutor;

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
     * Срок наказания
     */
    long duration;

    /**
     * Отменено ли наказания
     */
    boolean cancelled;

}
