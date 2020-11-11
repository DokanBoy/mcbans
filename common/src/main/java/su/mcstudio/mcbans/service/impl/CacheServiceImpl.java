package su.mcstudio.mcbans.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Singleton;
import lombok.NonNull;
import su.mcstudio.mcbans.model.Violation;
import su.mcstudio.mcbans.service.CacheService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.11.2020 20:26
 */
@Singleton
public class CacheServiceImpl implements CacheService {

    private final Cache<UUID, List<Violation>> cache = CacheBuilder.newBuilder()
                                                                   .expireAfterAccess(5L, TimeUnit.MINUTES)
                                                                   .expireAfterWrite(15, TimeUnit.MINUTES)
                                                                   .maximumSize(100)
                                                                   .build();

    @Override
    public boolean isCached(@NonNull UUID user) {
        return cache.getIfPresent(user) == null;
    }

    @Override
    public void addToCache(@NonNull UUID user, @NonNull List<Violation> violations) {
        cache.put(user, violations);
    }

    @Override
    public @NonNull Optional<List<Violation>> getCachedUser(@NonNull UUID user) {
        return Optional.ofNullable(cache.getIfPresent(user));
    }

}
