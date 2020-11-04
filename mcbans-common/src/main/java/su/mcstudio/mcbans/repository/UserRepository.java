package su.mcstudio.mcbans.repository;

import lombok.NonNull;
import su.mcstudio.mcbans.model.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:39
 */
public interface UserRepository {

    @NonNull User createUser(@NonNull UUID uuid);

    Optional<User> findUser(@NonNull UUID uuid);

    void updateUser(@NonNull User user);

    void removeUser(@NonNull User user);

}
