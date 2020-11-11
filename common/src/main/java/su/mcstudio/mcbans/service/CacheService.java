package su.mcstudio.mcbans.service;

import lombok.NonNull;
import su.mcstudio.mcbans.model.Violation;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 05.11.2020 17:44
 */
public interface CacheService {

    @NonNull List<Violation> get(@NonNull UUID playerId);

    void refresh(@NonNull UUID playerId);

}
