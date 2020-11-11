package su.mcstudio.mcbans.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.NonNull;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.service.CacheService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.11.2020 20:26
 */
@Singleton
public class CacheServiceImpl implements CacheService {

    private final ViolationRepository violationRepository;
    private final Cache<UUID, List<Violation>> cache = CacheBuilder.newBuilder()
                                                                   .expireAfterAccess(5L, TimeUnit.MINUTES)
                                                                   .expireAfterWrite(15, TimeUnit.MINUTES)
                                                                   .maximumSize(100)
                                                                   .build();

    @Inject
    public CacheServiceImpl(ViolationRepository violationRepository) {
        this.violationRepository = violationRepository;
    }


    @Override
    public @NonNull List<Violation> get(@NonNull UUID playerId) {
        return cache.asMap().computeIfAbsent(playerId, violationRepository::findByPlayerViolation);
    }

}
