package su.mcstudio.mcbans.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.NonNull;
import lombok.SneakyThrows;
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
    private final Function<UUID, List<Violation>> kvResolver;
    private final LoadingCache<UUID, List<Violation>> cache = CacheBuilder.newBuilder()
                                                                          .expireAfterAccess(5L, TimeUnit.MINUTES)
                                                                          .expireAfterWrite(15L, TimeUnit.MINUTES)
                                                                          .maximumSize(100)
                                                                          .build(new CacheLoader<UUID, List<Violation>>() {
                                                                              @Override
                                                                              public List<Violation> load(UUID key) {
                                                                                  return kvResolver.apply(key);
                                                                              }
                                                                          });

    @Inject
    public CacheServiceImpl(ViolationRepository violationRepository) {
        this.violationRepository = violationRepository;
        this.kvResolver = violationRepository::findByPlayerViolation;
    }

    @Override
    @SneakyThrows
    public @NonNull List<Violation> get(@NonNull UUID playerId) {
        return cache.get(playerId);
    }

    @Override
    public void refresh(@NonNull UUID playerId) {
        cache.refresh(playerId);
    }

}
