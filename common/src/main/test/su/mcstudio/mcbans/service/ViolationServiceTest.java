package su.mcstudio.mcbans.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.service.impl.ViolationServiceImpl;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 12.11.2020 22:04
 */
class ViolationServiceTest {

    private static ViolationRepository repository;
    private static CacheService cacheService;

    @BeforeAll
    static void init() {
        repository = Mockito.mock(ViolationRepository.class);
        cacheService = Mockito.mock(CacheService.class);
    }

    @Test
    void checkMutedPlayer() {
        ViolationService violationService = new ViolationServiceImpl(repository, cacheService);
        UUID playerId = UUID.randomUUID();

        Violation violation = violationService.mutePlayer(playerId, UUID.randomUUID(), "TEST REASON", Duration.ofMinutes(5).toMillis());

    }
}