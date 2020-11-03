package su.mcstudio.mcbans.service;

import lombok.NonNull;
import su.mcstudio.mcbans.model.punishment.Punishment;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:21
 */
public interface PunishmentService {

    Punishment kickPlayer(@NonNull UUID uuid, @NonNull Duration duration);

    Punishment banPlayer(@NonNull UUID uuid, @NonNull Duration duration, @NonNull String reason);

    Punishment mutePlayer(@NonNull UUID uuid, @NonNull Duration duration, @NonNull String reason);
}
