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
public class ViolationServiceImpl implements ViolationService {

    private final ViolationRepository violationRepository;

    @Inject
    public ViolationServiceImpl(@NonNull ViolationRepository repository) {
        this.violationRepository = repository;
    }

    @Override
    public Violation kickPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        final Violation violation = Violation.builder()
                                             .id(UUID.randomUUID())
                                             .player(playerId)
                                             .executor(executorId)
                                             .type(ViolationType.KICK)
                                             .reason(reason)
                                             .violationTime(System.currentTimeMillis())
                                             .build();
        Events.call(new KickEvent(
                        violation.getPlayer(),
                        violation.getExecutor(),
                        violation.getReason(),
                        violation.getViolationTime()
                )
        );
        return violationRepository.saveViolation(violation);
    }

    @Override
    public Violation banPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration) {
        final Violation violation = Violation.builder()
                                             .id(UUID.randomUUID())
                                             .player(playerId)
                                             .executor(executorId)
                                             .type(ViolationType.BAN)
                                             .reason(reason)
                                             .violationTime(System.currentTimeMillis())
                                             .duration(duration)
                                             .build();
        Events.call(new BanEvent(
                        violation.getPlayer(),
                        violation.getExecutor(),
                        violation.getReason(),
                        violation.getViolationTime(),
                        violation.getDuration()
                )
        );
        return violationRepository.saveViolation(violation);
    }

    @Override
    public Violation mutePlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason, long duration) {
        final Violation violation = Violation.builder()
                                             .id(UUID.randomUUID())
                                             .player(playerId)
                                             .executor(executorId)
                                             .type(ViolationType.MUTE)
                                             .reason(reason)
                                             .violationTime(System.currentTimeMillis())
                                             .duration(duration)
                                             .build();
        Events.call(new MuteEvent(
                        violation.getPlayer(),
                        violation.getExecutor(),
                        violation.getReason(),
                        violation.getViolationTime(),
                        violation.getDuration()
                )
        );
        return violationRepository.saveViolation(violation);
    }

    @Override
    public @NonNull List<Violation> unbanPlayer(@NonNull UUID playerId, @Nullable UUID executorId, @NonNull String reason) {
        return violationRepository.findByPlayerViolation(playerId)
                                  .stream()
                                  .filter(vl -> vl.getType() == ViolationType.BAN)
                                  .filter(Violation::isCancelled)
                                  .filter(vl -> (vl.getViolationTime() + vl.getDuration()) > System.currentTimeMillis())
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
                                  .filter(vl -> (vl.getViolationTime() + vl.getDuration()) > System.currentTimeMillis())
                                  .peek(vl -> vl.setCancelled(true))
                                  .peek(violationRepository::updateViolation)
                                  .collect(Collectors.toList());
    }

    @Override
    public @NonNull Optional<Violation> activeMute(@NonNull UUID playerId) {
        final List<Violation> foundViolations = violationRepository.findByPlayerViolation(playerId);
        if (foundViolations.isEmpty()) return Optional.empty();

        return foundViolations.stream()
                              .filter(vl -> vl.getType() == ViolationType.MUTE)
                              .filter(vl -> !vl.isCancelled())
                              .filter(vl -> (vl.getViolationTime() + vl.getDuration()) > System.currentTimeMillis())
                              .findFirst();
    }

    @Override
    public @NonNull Optional<Violation> activeBan(@NonNull UUID playerId) {
        final List<Violation> foundViolations = violationRepository.findByPlayerViolation(playerId);
        if (foundViolations.isEmpty()) return Optional.empty();

        return foundViolations.stream()
                              .filter(vl -> vl.getType() == ViolationType.BAN)
                              .filter(vl -> !vl.isCancelled())
                              .filter(vl -> (vl.getViolationTime() + vl.getDuration()) > System.currentTimeMillis())
                              .findFirst();
    }

}
