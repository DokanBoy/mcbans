package su.mcstudio.mcbans.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.model.ViolationType;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.service.impl.CacheServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 12.11.2020 21:48
 */
class CacheServiceTest {

    private static ViolationRepository repository;

    @BeforeAll
    static void init() {
        repository = Mockito.mock(ViolationRepository.class);
    }

    @Test
    void checkCacheSavingOnViolation() {
        UUID playerId = UUID.randomUUID();
        Mockito.when(repository.findByPlayerViolation(playerId)).thenReturn(new ArrayList<>());
        CacheServiceImpl cacheService = new CacheServiceImpl(repository);
        List<Violation> violations = cacheService.get(playerId);
        violations.add(null);
        assertTrue(cacheService.get(playerId).size() > 0);
    }

    @Test
    void checkCacheAfterChangeInRepository() {
        List<Violation> studRepo = new ArrayList<>();

        UUID playerId = UUID.randomUUID();
        Violation violation = Violation.builder()
                                       .id(UUID.randomUUID())
                                       .player(playerId)
                                       .type(ViolationType.BAN)
                                       .reason("TEST REASON")
                                       .build();
        Mockito.when(repository.findByPlayerViolation(playerId)).thenReturn(studRepo);
        Mockito.when(repository.saveViolation(violation)).then((Answer<Violation>) invocation -> {
            studRepo.add(violation);
            return violation;
        });

        CacheServiceImpl cacheService = new CacheServiceImpl(repository);

        int primary = cacheService.get(playerId).size();

        repository.saveViolation(violation);
        int secondary = cacheService.get(playerId).size();

        assertTrue(primary < secondary);
    }

}