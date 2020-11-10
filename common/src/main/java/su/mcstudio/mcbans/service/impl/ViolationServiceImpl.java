package su.mcstudio.mcbans.service.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.model.ViolationType;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.service.ViolationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 16:12
 */
@Singleton
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ViolationServiceImpl implements ViolationService {

    ViolationRepository violationRepository;

    @Inject
    public ViolationServiceImpl(@NonNull ViolationRepository repository) {
        this.violationRepository = repository;
    }


    @Override
    public Violation kickPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        Violation violation = Violation.builder()
                                       .violationId(UUID.randomUUID())
                                       .player(playerId)
                                       .executor(executorId)
                                       .type(ViolationType.KICK)
                                       .reason(reason)
                                       .violationTime(System.currentTimeMillis())
                                       .build();
        return violationRepository.saveViolation(violation);
    }

    @Override
    public Violation banPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration) {
        Violation violation = Violation.builder()
                                       .violationId(UUID.randomUUID())
                                       .player(playerId)
                                       .executor(executorId)
                                       .type(ViolationType.BAN)
                                       .reason(reason)
                                       .violationTime(System.currentTimeMillis())
                                       .duration(duration)
                                       .build();
        return violationRepository.saveViolation(violation);
    }

    @Override
    public Violation mutePlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration) {
        Violation violation = Violation.builder()
                                       .violationId(UUID.randomUUID())
                                       .player(playerId)
                                       .executor(executorId)
                                       .type(ViolationType.MUTE)
                                       .reason(reason)
                                       .violationTime(System.currentTimeMillis())
                                       .duration(duration)
                                       .build();
        return violationRepository.saveViolation(violation);
    }

    @Override
    public @NonNull List<Violation> unbanPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        return violationRepository.findByPlayerViolation(playerId).stream()
                                  .filter(vl -> vl.getType() == ViolationType.BAN)
                                  .filter(Violation::isCancelled)
                                  .filter(vl -> (vl.getViolationTime() + vl.getDuration()) < System.currentTimeMillis())
                                  .peek(violation -> violation.setCancelled(true))
                                  .peek(violationRepository::updateViolation)
                                  .collect(Collectors.toList());
    }

    @Override
    public @NonNull List<Violation> unmutePlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        return violationRepository.findByPlayerViolation(playerId)
                                  .stream()
                                  .filter(vl -> vl.getType() == ViolationType.MUTE)
                                  .filter(Violation::isCancelled)
                                  .filter(vl -> (vl.getViolationTime() + vl.getDuration()) < System.currentTimeMillis())
                                  .peek(vl -> vl.setCancelled(true))
                                  .peek(violationRepository::updateViolation)
                                  .collect(Collectors.toList());
    }

    @Override
    public Optional<Violation> activeMute(@NonNull UUID playerId) {
        List<Violation> foundViolations = violationRepository.findByPlayerViolation(playerId);
        if (foundViolations == null || foundViolations.isEmpty()) return Optional.empty();

        return foundViolations.stream()
                              .filter(vl -> vl.getType() == ViolationType.MUTE)
                              .filter(Violation::isCancelled)
                              .filter(vl -> (vl.getViolationTime() + vl.getDuration()) < System.currentTimeMillis())
                              .findFirst();
    }

    @Override
    public Optional<Violation> activeBan(@NonNull UUID playerId) {
        List<Violation> foundViolations = violationRepository.findByPlayerViolation(playerId);
        if (foundViolations == null || foundViolations.isEmpty()) return Optional.empty();

        return foundViolations.stream()
                              .filter(vl -> vl.getType() == ViolationType.BAN)
                              .filter(Violation::isCancelled)
                              .filter(vl -> (vl.getViolationTime() + vl.getDuration()) < System.currentTimeMillis())
                              .findFirst();
    }

}
