package su.mcstudio.mcbans.repository.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.simplix.core.database.sql.SqlDatabaseConnection;
import dev.simplix.core.database.sql.function.ResultSetTransformer;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import su.mcstudio.mcbans.model.User;
import su.mcstudio.mcbans.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:46
 */
@Singleton
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepositoryImpl implements UserRepository {

    SqlDatabaseConnection connection;
    ResultSetTransformer<User> USER_TRANSFORMER = resultSet -> User.builder()
                                                                   .uuid(UUID.fromString(resultSet.getString("uuid")))
                                                                   .violations(Lists.newArrayList())
                                                                   .build();

    @Inject
    public UserRepositoryImpl(SqlDatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public @NonNull User createUser(@NonNull UUID uuid) {
        Optional<User> cachedUser = findUser(uuid);
        return cachedUser.orElseGet(() -> connection.query("INSERT INTO mcbans_users (uuid) VALUES (?)",
                ps -> ps.setString(1, uuid.toString()),
                USER_TRANSFORMER));
    }

    @Override
    public Optional<User> findUser(@NonNull UUID uuid) {
        return Optional.ofNullable(connection.query("SELECT * FROM mcbans_users WHERE uuid=?",
                ps -> ps.setString(1, uuid.toString()),
                USER_TRANSFORMER));
    }

    @Override
    public void updateUser(@NonNull User user) {
        connection.prepare("UPDATE mcbans_users SET uuid= ?", ps -> ps.setString(1, user.getUuid().toString()));
    }

    @Override
    public void removeUser(@NonNull User user) {

    }
}
