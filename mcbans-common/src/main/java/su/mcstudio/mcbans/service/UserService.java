package su.mcstudio.mcbans.service;

import lombok.NonNull;
import su.mcstudio.mcbans.model.violation.Violation;
import su.mcstudio.mcbans.model.violation.ViolationType;
import su.mcstudio.mcbans.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:30
 */
public interface UserService {

    @NonNull User findOrCreate(@NonNull UUID uuid);

    @NonNull List<Violation> findAllPunishments();

    @NonNull List<Violation> findPunishmentByType(@NonNull ViolationType type);

}
