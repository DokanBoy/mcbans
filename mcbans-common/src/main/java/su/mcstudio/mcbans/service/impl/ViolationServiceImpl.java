package su.mcstudio.mcbans.service.impl;

import com.google.inject.Singleton;
import lombok.NonNull;
import su.mcstudio.mcbans.model.violation.Violation;
import su.mcstudio.mcbans.service.ViolationService;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 16:12
 */
@Singleton
public class ViolationServiceImpl implements ViolationService {
    @Override
    public Violation kickPlayer(@NonNull UUID uuid, @NonNull Duration duration) {
        return null;
    }

    @Override
    public Violation banPlayer(@NonNull UUID uuid, @NonNull Duration duration, @NonNull String reason) {
        return null;
    }

    @Override
    public Violation mutePlayer(@NonNull UUID uuid, @NonNull Duration duration, @NonNull String reason) {
        return null;
    }
}
