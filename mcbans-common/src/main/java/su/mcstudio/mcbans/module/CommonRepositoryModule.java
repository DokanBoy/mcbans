package su.mcstudio.mcbans.module;

import com.google.inject.Binder;
import de.leonhard.storage.Yaml;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.database.sql.SqlDatabaseConnection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.repository.UserRepository;
import su.mcstudio.mcbans.repository.impl.ViolationRepositoryImpl;
import su.mcstudio.mcbans.repository.impl.UserRepositoryImpl;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:52
 */
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonRepositoryModule extends AbstractSimplixModule {

    Yaml yaml;

    @Override
    public void configure(Binder binder) {
        super.configure(binder);

        binder.bind(SqlDatabaseConnection.class).toInstance(SqlDatabaseConnection.hikari(
                yaml.getString("mysql.host"),
                yaml.getString("mysql.user"),
                yaml.getString("mysql.password"),
                yaml.getString("mysql.port"),
                yaml.getString("mysql.database"))
        );

        binder.bind(ViolationRepository.class).to(ViolationRepositoryImpl.class);
        binder.bind(UserRepository.class).to(UserRepositoryImpl.class);
    }
}
