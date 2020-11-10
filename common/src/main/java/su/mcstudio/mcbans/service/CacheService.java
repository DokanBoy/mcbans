package su.mcstudio.mcbans.service;

import lombok.NonNull;
import su.mcstudio.mcbans.model.Violation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 05.11.2020 17:44
 */
public interface CacheService {

    boolean isCached(@NonNull UUID user);

    void addToCache(@NonNull UUID user, @NonNull List<Violation> violations);

    @NonNull Optional<List<Violation>> getCachedUser(@NonNull UUID user);

}
