package su.mcstudio.mcbans.service.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.simplix.core.common.event.Events;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import su.mcstudio.mcbans.events.BanEvent;
import su.mcstudio.mcbans.events.KickEvent;
import su.mcstudio.mcbans.events.MuteEvent;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.model.ViolationType;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.service.CacheService;
import su.mcstudio.mcbans.service.ViolationService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 16:12
 */
@Singleton
public class ViolationServiceImpl implements ViolationService {

    private final ViolationRepository violationRepository;
    private final CacheService cacheService;

    @Inject
    public ViolationServiceImpl(@NonNull ViolationRepository repository, @NonNull CacheService cacheService) {
        this.violationRepository = repository;
        this.cacheService = cacheService;
    }

    @Override
    public Violation kickPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        final Violation violation = violationRepository.saveViolation(Violation.builder()
                                                                               .id(UUID.randomUUID())
                                                                               .player(playerId)
                                                                               .executor(executorId)
                                                                               .type(ViolationType.KICK)
                                                                               .reason(reason)
                                                                               .violationTime(System.currentTimeMillis())
                                                                               .build());
        cacheService.refresh(playerId);
        Events.call(new KickEvent(
                        violation.getPlayer(),
                        violation.getExecutor(),
                        violation.getReason(),
                        violation.getViolationTime()
                )
        );
        return violation;
    }

    @Override
    public Violation banPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration) {
        final Violation violation = violationRepository.saveViolation(Violation.builder()
                                                                               .id(UUID.randomUUID())
                                                                               .player(playerId)
                                                                               .executor(executorId)
                                                                               .type(ViolationType.BAN)
                                                                               .reason(reason)
                                                                               .violationTime(System.currentTimeMillis())
                                                                               .duration(duration)
                                                                               .build());
        cacheService.refresh(playerId);
        Events.call(new BanEvent(
                        violation.getPlayer(),
                        violation.getExecutor(),
                        violation.getReason(),
                        violation.getViolationTime(),
                        violation.getDuration()
                )
        );
        return violation;
    }

    @Override
    public Violation mutePlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration) {
        final Violation violation = violationRepository.saveViolation(Violation.builder()
                                                                               .id(UUID.randomUUID())
                                                                               .player(playerId)
                                                                               .executor(executorId)
                                                                               .type(ViolationType.MUTE)
                                                                               .reason(reason)
                                                                               .violationTime(System.currentTimeMillis())
                                                                               .duration(duration)
                                                                               .build());

        cacheService.refresh(playerId);
        Events.call(new MuteEvent(
                        violation.getPlayer(),
                        violation.getExecutor(),
                        violation.getReason(),
                        violation.getViolationTime(),
                        violation.getDuration()
                )
        );
        return violation;
    }

    @Override
    public @NonNull List<Violation> unbanPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        List<Violation> violations = findActiveViolationsByType(playerId, ViolationType.BAN).stream()
                                                                                            .peek(vl -> vl.setCancelled(true))
                                                                                            .peek(vl -> vl.setDeExecutor(executorId))
                                                                                            .peek(violationRepository::updateViolation)
                                                                                            .collect(Collectors.toList());
        cacheService.refresh(playerId);
        return violations;
    }

    @Override
    public @NonNull List<Violation> unmutePlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        List<Violation> violations = findActiveViolationsByType(playerId, ViolationType.MUTE).stream()
                                                                                             .peek(vl -> vl.setCancelled(true))
                                                                                             .peek(vl -> vl.setDeExecutor(executorId))
                                                                                             .peek(violationRepository::updateViolation)
                                                                                             .collect(Collectors.toList());
        cacheService.refresh(playerId);
        return violations;
    }

    @Override
    public @NonNull List<Violation> activeMutes(@NonNull UUID playerId) {
        return findActiveViolationsByType(playerId, ViolationType.MUTE);
    }

    @Override
    public @NonNull List<Violation> activeBans(@NonNull UUID playerId) {
        return findActiveViolationsByType(playerId, ViolationType.BAN);
    }

    private @NonNull List<Violation> findActiveViolationsByType(@NonNull UUID playerId, @NonNull ViolationType type) {
        final List<Violation> foundViolations = cacheService.get(playerId);
        if (foundViolations.isEmpty()) return foundViolations;

        return foundViolations.stream()
                              .filter(vl -> vl.getType() == type)
                              .filter(vl -> !vl.isCancelled())
                              .filter(vl -> (vl.getViolationTime() + vl.getDuration()) > System.currentTimeMillis())
                              .collect(Collectors.toList());
    }

}
