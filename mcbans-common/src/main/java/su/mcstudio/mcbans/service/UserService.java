package su.mcstudio.mcbans.service;

import lombok.NonNull;
import su.mcstudio.mcbans.model.punishment.Punishment;
import su.mcstudio.mcbans.model.punishment.PunishmentType;
import su.mcstudio.mcbans.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:30
 */
public interface UserService {

    @NonNull User findOrCreate(@NonNull UUID uuid);

    @NonNull List<Punishment> findAllPunishments();

    @NonNull List<Punishment> findPunishmentByType(@NonNull PunishmentType type);

}
