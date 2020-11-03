package su.mcstudio.mcbans.model.punishment;

import lombok.NonNull;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:23
 */
public abstract class Punishment {

    @NonNull UUID getUser;

    @NonNull UUID getExecutor;

    @NonNull PunishmentType getType;

    @NonNull Duration getDuration;

}
