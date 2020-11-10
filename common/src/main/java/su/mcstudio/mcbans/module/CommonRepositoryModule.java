package su.mcstudio.mcbans.module;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Binder;
import de.leonhard.storage.Yaml;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.database.sql.SqlDatabaseConnection;
import lombok.AllArgsConstructor;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.repository.impl.ViolationRepositoryImpl;
import su.mcstudio.mcbans.util.query.QueryFactory;

import java.util.concurrent.Executors;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:52
 */
@AllArgsConstructor
public class CommonRepositoryModule extends AbstractSimplixModule {

    private final Yaml yaml;

    private static ListeningExecutorService getListeningExecutorService() {
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    @Override
    public void configure(Binder binder) {
        super.configure(binder);

        final SqlDatabaseConnection databaseConnection = SqlDatabaseConnection.hikari(
                yaml.getString("data.address"),
                yaml.getString("data.username"),
                yaml.getString("data.password"),
                yaml.getString("data.port"),
                yaml.getString("data.database")
        );
        binder.bind(SqlDatabaseConnection.class).toInstance(databaseConnection);

        final QueryFactory queryFactory = new QueryFactory(
                CommonRepositoryModule::getListeningExecutorService,
                databaseConnection.getDataSource()
        );
        binder.bind(QueryFactory.class).toInstance(queryFactory);

        binder.bind(ViolationRepository.class).to(ViolationRepositoryImpl.class);
    }

}
