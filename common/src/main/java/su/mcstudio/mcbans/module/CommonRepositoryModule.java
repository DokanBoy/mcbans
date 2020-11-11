package su.mcstudio.mcbans.module;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Binder;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.database.sql.SqlDatabaseConnection;
import dev.simplix.core.database.sql.util.QueryFactory;
import lombok.extern.slf4j.Slf4j;
import ninja.leaping.configurate.ConfigurationNode;
import su.mcstudio.mcbans.repository.ViolationRepository;
import su.mcstudio.mcbans.repository.impl.ViolationRepositoryImpl;

import java.util.Calendar;
import java.util.concurrent.Executors;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 17:52
 */
@Slf4j
public class CommonRepositoryModule extends AbstractSimplixModule {

    public static final String OPTIONS =
            ("?jdbcCompliantTruncation=false&useUnicode=true&characterEncoding=utf8"
                    + "&serverTimezone={timezone}&zeroDateTimeBehavior=convertToNull&autoReconnect=true"
                    + "&zeroDateTimeBehavior=convertToNull&max_allowed_packet=512M")
                    .replace("{timezone}", Calendar.getInstance().getTimeZone().getID());

    private final ConfigurationNode dbCredentials;

    public CommonRepositoryModule(ConfigurationNode dbCredentials) {
        this.dbCredentials = dbCredentials;
    }

    private static ListeningExecutorService getListeningExecutorService() {
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    @Override
    public void configure(Binder binder) {
        super.configure(binder);

        final SqlDatabaseConnection databaseConnection = SqlDatabaseConnection.hikari(
                dbCredentials.getNode("address").getString("127.0.0.1"),
                dbCredentials.getNode("username").getString("root"),
                dbCredentials.getNode("password").getString(""),
                dbCredentials.getNode("port").getString("3306"),
                dbCredentials.getNode("database").getString("mcbans")
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
