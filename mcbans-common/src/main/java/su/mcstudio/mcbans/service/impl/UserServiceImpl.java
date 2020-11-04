package su.mcstudio.mcbans.service.impl;

import com.google.inject.Singleton;
import lombok.NonNull;
import su.mcstudio.mcbans.model.User;
import su.mcstudio.mcbans.model.violation.Violation;
import su.mcstudio.mcbans.model.violation.ViolationType;
import su.mcstudio.mcbans.service.UserService;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 16:12
 */
@Singleton
public class UserServiceImpl implements UserService {
    @Override
    public @NonNull User findOrCreate(@NonNull UUID uuid) {
        return null;
    }

    @Override
    public @NonNull List<Violation> findAllPunishments() {
        return null;
    }

    @Override
    public @NonNull List<Violation> findPunishmentByType(@NonNull ViolationType type) {
        return null;
    }
}
